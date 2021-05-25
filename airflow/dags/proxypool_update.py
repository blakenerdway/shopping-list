import logging
from datetime import timedelta, datetime
import socket, struct


from proxy_dag_config import Config as config
from airflow import DAG
from airflow.operators.python import PythonOperator
from airflow.utils.dates import days_ago
from airflow.providers.mysql.operators.mysql import MySqlOperator
from proxypool_operator import ProxyPoolOperator
from airflow import settings
from airflow.models import Connection

conn_id = 'shopping_list_db'
conn = Connection(
        conn_id=conn_id,
        conn_type='mysql',
        host='localhost',
        login='root',
        password='password!',
        port='3306'
    )
session = settings.Session()
conn_name = session\
.query(Connection)\
.filter(Connection.conn_id == conn.conn_id)\
.first()

if str(conn_name) == str(conn_id):
    logging.info(f"Connection {conn_id} already exists")

session.add(conn)
session.commit()
logging.info(Connection.log_info(conn))
logging.info(f'Connection {conn_id} is created')


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
    records = ""
    for proxy_obj in proxies:
        int_ip = struct.unpack("!L", socket.inet_aton(proxy_obj.proxy.ip_address))[0]
        is_https = proxy_obj.proxy.https == "yes"
        records += f'INSERT INTO proxy (ip, port, https, health) VALUES({int_ip}, {proxy_obj.proxy.port}, {is_https}, {proxy_obj.health});'

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

    create_inserts = PythonOperator(
        task_id="proxy_health_to_sql_transform",
        python_callable=create_sql,
        provide_context=True
    )

    insert_records = MySqlOperator(
        mysql_conn_id="shopping_list_db",
        task_id="store_proxy_health",
        sql="{{ ti.xcom_pull(key='proxy_records', task_ids='proxy_health_to_sql_transform') }}"
    )

    start >> proxypool >> create_inserts >> insert_records
