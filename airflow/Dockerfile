FROM apache/airflow:master-python3.8

COPY requirements.txt .

RUN pip install -r requirements.txt

RUN airflow info
