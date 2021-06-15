FROM apache/airflow:master-python3.8

COPY requirements.txt .
COPY items.json .
COPY airflow.cfg .

RUN pip install -r requirements.txt