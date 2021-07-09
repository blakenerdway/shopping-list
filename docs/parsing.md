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
```
$ JOB_CLASS_NAME="com.job.ClassName"
$ JM_CONTAINER=$(docker ps --filter name=jobmanager --format="{{.ID}}"))
$ docker cp path/to/jar "${JM_CONTAINER}":/job.jar
$ docker exec -t -i "${JM_CONTAINER}" flink run -d -c ${JOB_CLASS_NAME} /shopping-list_parser.jar \
–runner=FlinkRunner –other-parameters
```
