# Composer/Airflow
## Setup
### Pycharm
Mark the `config`, and `plugins` folders as sources in Pycharm.

## Airflow
Start docker container using `docker-compose up`  (if you need to rebuild: `docker-compose up --build --force-recreate --no-deps`).

After starting up the docker container, ensure that in the connections tab, the `google_cloud_default` connect has the keyfile path set for the json file

