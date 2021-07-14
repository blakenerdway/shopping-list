from datetime import timedelta, datetime

import requests
from airflow import DAG
from airflow.utils.dates import days_ago
from airflow.operators.python import PythonOperator
import os
import json
import logging

default_args = {
    'owner': 'Blake Ordway',
    'depends_on_past': False,
    'email': ['blakeordway2@gmail.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5)
}

f = open('items.json')
json_items = json.load(f)


def target_request(items, stores):
    payload = {'product': items, 'store': stores}
    print(payload)
    requests.put("http://localhost:8480/target/products", params=payload)


def walmart_request(items, stores):
    payload = {'product': items, 'store': stores}
    print(payload)
    requests.put("http://localhost:8480/walmart/products", params=payload)

def create_target_dag(dag_id):
    folder = f'/opt/airflow/logs/{dag_id}'
    if not os.path.exists(folder):
        os.makedirs(folder)

    dag = DAG(dag_id=dag_id,
              default_args=default_args,
              description=f"Target website scraper scheduler dag",
              schedule_interval='0 0 * * *',
              start_date=datetime.now() - timedelta(days=1),
              catchup=False,
              is_paused_upon_creation=True)
    with dag:


        tasks = []
        counter = 0
        for grocery_type in json_items:
            search_items = json_items[grocery_type]
            tasks.append(PythonOperator(task_id=f'{grocery_type}-Target',
                                        python_callable=target_request,
                                        op_kwargs={'items': search_items, 'stores': [1273, 686]}))
            if counter != 0:
                tasks[counter - 1] >> tasks[counter]
            counter += 1

    return dag


def create_walmart_dag(dag_id):
    folder = f'/opt/airflow/logs/{dag_id}'
    if not os.path.exists(folder):
        os.makedirs(folder)

    dag = DAG(dag_id=dag_id,
              default_args=default_args,
              description=f"Walmart website scraper scheduler dag",
              schedule_interval='0 8 * * *',
              start_date=datetime.now() - timedelta(days=1),
              catchup=False,
              is_paused_upon_creation=True)
    with dag:
        tasks = []
        counter = 0
        for grocery_type in json_items:
            search_items = json_items[grocery_type]
            tasks.append(PythonOperator(task_id=f'{grocery_type}-Walmart',
                                        python_callable=walmart_request,
                                        op_kwargs={'items': search_items, 'stores': [1746]}))
            if counter != 0:
                tasks[counter - 1] >> tasks[counter]
            counter += 1


target_dag = 'target-grocery-updates'
globals()[target_dag] = create_target_dag(target_dag)


walmart_dag = 'walmart-grocery-updates'
globals()[walmart_dag] = create_target_dag(walmart_dag)

