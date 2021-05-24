# Airflow
## Setup
### Requirements
- pip >= 21.1.2
- Python >= 3.8.10
### Pycharm
Mark the `config`, and `plugins` folders as sources in Pycharm.

## Airflow
### Version
Using airflow version 2.1. If you need to change the version, you will have to change the Dockerfile as well as the requirements.txt to use the proper version

Start docker container using `docker-compose up`  (if you need to rebuild: `docker-compose up --build --force-recreate --no-deps`).