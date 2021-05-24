import os
from datetime import timedelta, datetime
import socket, struct
from six.moves.urllib.parse import quote_plus


from proxy_dag_config import Config as config
from airflow import DAG
from airflow.operators.python import PythonOperator
from airflow.utils.dates import days_ago
from proxypool_operator import ProxyPoolOperator

# The connections below are created using one of the standard approaches - via environment
# variables named AIRFLOW_CONN_* . The connections can also be created in the database
# of AIRFLOW (using command line or UI).
mysql_kwargs = dict(
    user=quote_plus('root'),
    password=quote_plus('grocerez-admin-inst1'),
    public_port=3306,
    public_ip=quote_plus('0.0.0.0'),
    project_id=quote_plus('skilful-rite-279019'),
    location=quote_plus(os.environ.get('GCP_REGION', 'us-central1')),
    instance=quote_plus('grocerez-mysql-inst1'),
    database=quote_plus('proxy_info')
)

# MySQL: connect via proxy over TCP (specific proxy version)
os.environ['AIRFLOW_CONN_MYSQL_TCP'] = \
    "gcpcloudsql://{user}:{password}@{public_ip}:{public_port}/{database}?" \
    "database_type=mysql&" \
    "project_id={project_id}&" \
    "location={location}&" \
    "instance={instance}&" \
    "use_proxy=True&" \
    "sql_proxy_version=v1.13&" \
    "extra__google_cloud_platform__key_path=/usr/local/airflow/key.json" \
    "sql_proxy_use_tcp=True".format(**mysql_kwargs)


default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': days_ago(1),
    'email': ['blakeordway2@gmail.com'],
    'email_on_failure': True,
    'email_on_retry': True,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
    # 'queue': 'bash_queue',
    # 'pool': 'backfill',
    # 'priority_weight': 10,
    # 'end_date': datetime(2016, 1, 1),
    # 'wait_for_downstream': False,
    # 'dag': dag,
    # 'sla': timedelta(hours=2),
    # 'execution_timeout': timedelta(seconds=300),
    # 'on_failure_callback': some_function,
    # 'on_success_callback': some_other_function,
    # 'on_retry_callback': another_function,
    # 'sla_miss_callback': yet_another_function,
    # 'trigger_rule': 'all_success'
}


def dummy_callable():
    return f"Attempting to find proxies"


def create_sql(**kwargs):
    proxies = kwargs['ti'].xcom_pull(key='proxies', task_ids='proxy_pool')
    health = ""
    records = ""
    for proxy_obj in proxies:
        int_ip = struct.unpack("!L", socket.inet_aton(proxy_obj.proxy.ip_address))[0]
        is_https = proxy_obj.proxy.https == "yes"
        records += f'INSERT INTO proxy (ip, port, https, average_health, most_recent_health) VALUES({int_ip}, {proxy_obj.proxy.port}, {is_https}, {proxy_obj.health}, {proxy_obj.health});'

    kwargs['ti'].xcom_push(key='proxy_health', value=health)
    kwargs['ti'].xcom_push(key='proxy_records', value=records)


with DAG(dag_id="proxy_update",
         description=f"Update latest proxy information",
         schedule_interval=timedelta(hours=1),
         start_date=datetime.now() - timedelta(hours=1),
         catchup=False,
         is_paused_upon_creation=False) as dag:
    start = PythonOperator(
        task_id="starting_pipeline",
        python_callable=dummy_callable
    )

    proxypool = ProxyPoolOperator(
        task_id="proxy_pool",
        proxy_webpage=config.PROXY_WEBPAGE,
        number_of_proxies=config.NUMBER_OF_PROXIES,
        testing_url=config.TESTING_URL,
        max_workers=config.NUMBER_OF_PROXIES,
    )

    kafka_send = ProxyPoolOperator(
        task_id="send_to_db",
        python_callable=send_to_kafka,
        provide_context=True
    )

    # create_inserts = PythonOperator(
    #     task_id="proxy_health_to_sql_transform",
    #     python_callable=create_sql,
    #     provide_context=True
    # )

    # insert_records = CloudSqlQueryOperator(
    #     gcp_cloudsql_conn_id="mysql_tcp",
    #     task_id="store_proxy_health",
    #     sql="{{ ti.xcom_pull(key='proxy_records', task_ids='proxy_health_to_sql_transform') }}"
    # )

    start >> proxypool >> kafka_send
