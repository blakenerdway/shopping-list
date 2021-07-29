---
layout: default
title: Parsing
nav_order: 4
---

# Parsing data
Parsing is done using Apache Beam's pipeline SDK. Each pipeline is created as a "streaming" pipeline with no bounded source.
Data is received via a Kafka connector with a specific topic subscribed to. Pipelines subscribe to a single topic. 
Each pipeline is created to parse a specific data format from the Kafka topic. The topics are listed below.

| Supplier  | Product topic     | Store topic     |
|:----------|:------------------|:----------------|
| Target    | target.products   | target.stores   |
| Walmart   | walmart.products  | walmart.stores  |

The final step of each pipeline takes the output of the previous job and stores it into the MySQL database instance 
using a JDBC connector. 

## Specific data parsing
Information for how pipeline parses data can be found on the appropriate page.

## Running pipelines
The pipelines are bundled into a "fat JAR" (shadow JAR). This JAR is created using a Gradle plugin, 
run using `gradle(w) shadowJar`. In order to properly bundle the JAR, the Flink runner jar must be placed within the MANIFEST
so that the Beam SDK can find it. After creating the JAR, it should be copied to the Flink Job Manager container.
After being copied, the job should be submitted using the Flink CLI and passing in the class name.
(Instructions can be found [below](#submitting-a-job)). Each pipeline must be submitted as a separate job to the Flink
Job Manager. A script has been created for convenience to deploy all the pipelines for both Windows and Unix systems.

### Submitting a job
Linux:
```
$ JOB_CLASS_NAME="shoppinglist.beam.products.pipelines.TargetParse"
$ JM_CONTAINER=$(docker ps --filter name=jobmanager --format="{{.ID}}")
$ docker cp beam-pipelines.jar "${JM_CONTAINER}":/beam-pipelines.jar
$ docker exec -t -i "${JM_CONTAINER}" flink run -d -c ${JOB_CLASS_NAME} /beam-pipelines.jar \
--runner=FlinkRunner --bootstrapServers=localhost:9092 --inputTopics=target.products
```

Windows:
```
docker ps --filter name=jobmanager --format="{{.ID}}" > tmpFile
set /p JM_CONTAINER=< tmpFile
del tmpFile
set JOB_CLASS_NAME="shoppinglist.beam.products.pipelines.TargetParse"
docker cp beam-pipelines.jar %JM_CONTAINER%:/beam-pipelines.jar
docker exec -t -i "%JM_CONTAINER%" flink run -d -c %JOB_CLASS_NAME% /beam-pipelines.jar --runner=FlinkRunner --bootstrapServers=localhost:9092 --inputTopics=target.products
```

## Monitoring pipelines
Because the pipelines are run on Flink, the Flink dashboard displays different metrics about the pipelines. 
Pipeline metrics can be viewed on the dashboard, located at [localhost:8081](http://localhost:8081)