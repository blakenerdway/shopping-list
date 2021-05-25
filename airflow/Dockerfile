FROM apache/airflow:master-python3.8

COPY requirements.txt .

RUN pip install -r requirements.txt
# Add a connection to the local running mysql database
RUN airflow connections add 'shopping_list_db' \
    --conn-uri 'mysql://root:password!@localhost:3306/shopping-list'
