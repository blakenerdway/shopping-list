FROM apache/airflow:2.1.2-python3.8

COPY requirements.txt .
COPY items-test.json .
COPY airflow.cfg .

RUN pip install -r requirements.txt