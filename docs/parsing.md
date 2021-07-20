---
layout: default
title: Parsing
nav_order: 4
---

# Parsing data
Parsing is done using Apache Beam's pipeline SDK. 

## Running pipelines
Pipelines are run on an Apache Flink cluster

### Submitting a job
Linux:
```
$ JOB_CLASS_NAME="shoppinglist.beam.products.pipelines.TargetParse"
$ JM_CONTAINER=$(docker ps --filter name=jobmanager --format="{{.ID}}")
$ docker cp beam-pipelines.jar "${JM_CONTAINER}":/beam-pipelines.jar
$ docker exec -t -i "${JM_CONTAINER}" flink run -d -c ${JOB_CLASS_NAME} /beam-pipelines.jar \
–-runner=FlinkRunner --bootstrapServers=localhost:9092 --inputTopics=target.products
```

Windows:
```
docker ps --filter name=jobmanager --format="{{.ID}}" > tmpFile
set /p JM_CONTAINER=< tmpFile
del tmpFile
set JOB_CLASS_NAME="shoppinglist.beam.products.pipelines.TargetParse"
docker cp beam-pipelines.jar %JM_CONTAINER%:/beam-pipelines.jar
 docker exec -t -i "${JM_CONTAINER}" flink run -d -c ${JOB_CLASS_NAME} /beam-pipelines.jar –-runner=FlinkRunner --bootstrapServers=localhost:9092 --inputTopics=target.products
```