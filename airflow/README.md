# Airflow
## Setup
### Requirements
- pip >= 21.1.2
    - If get a cryptography package build error, try to run `easy_install -U pip`. The docker environment should not have this issue (Go Docker!).
- Python >= 3.8.10

### COnfiuration
Set environment variables for the sql database so it is loaded at startup. Set `airflow.cfg` file using volume mounting in docker-compose.yml


### Pycharm
Mark the `dags`, and `plugins` folders as sources in Pycharm.

## Airflow
### Version
Using airflow version 2.1. If you need to change the version, you will have to change the Dockerfile as well as the requirements.txt to use the proper version

Start docker container using `docker-compose up`  (if you need to rebuild: `docker-compose up --build --force-recreate --no-deps`).