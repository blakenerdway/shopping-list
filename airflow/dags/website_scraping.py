from datetime import timedelta, datetime
from airflow import DAG
from airflow.utils.dates import days_ago
from airflow.operators.python import PythonOperator
import os
import json

default_args = {
    'owner': 'Blake Ordway',
    'depends_on_past': False,
    'email': ['blakeordway2@gmail.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5)
}


def target_request(items, stores):
    pass


def walmart_request(items, stores):
    pass


def create_dag(dag_id, grocery_type, search_items):
    folder = f'/opt/airflow/logs/{dag_id}'
    if not os.path.exists(folder):
        os.makedirs(folder)

    dag = DAG(dag_id=dag_id,
              default_args=default_args,
              description=f"Update grocery {grocery_type} items",
              schedule_interval=timedelta(days=1),
              start_date=datetime.now() - timedelta(days=1),
              catchup=False,
              is_paused_upon_creation=True)
    with dag:
        target_items = PythonOperator(task_id=f'{grocery_type}-Target',
                                      python_callable=target_request,
                                      op_kwargs={'items': search_items, 'stores': [1273, 686]})
        walmart_items = PythonOperator(task_id=f'{grocery_type}-Walmart',
                                       python_callable=walmart_request,
                                       op_kwargs={'items': search_items, 'stores': [1746]}
                                       )
        [target_items, walmart_items]

    return dag


f = open('items.json')
items = json.load(f)
for grocery_type in items:
    dag_id = f'{grocery_type}-updates'
    search_items = items[grocery_type]
    globals()[dag_id] = create_dag(dag_id, grocery_type, search_items)
