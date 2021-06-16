# Shopping-list Beam
The goal of this module is to listen to an unbounded Kafka topic and parse the data coming in on the topic. Separating out the parsing and requesting of data allows
modules to be debugged separately and provides easier maintenance.

Beam provides a portable framework that can be used on top of multiple different runners, based on the need of the environment. In this situation, we run our Beam pipelines
on top of Apache Flink.

## Starting
### VM Parameters
Set VM arguments: `-Djava.util.logging.config.file=logging.properties`

### Runtime parameters

**Target Parse** - `--bootstrapServers=localhost:9092 --inputTopics=target.products`
- Bootstrap server should be set to kafka:9093 if inside a container/network 


## Monitoring
Pipeline runs can be monitored via the Apache Flink console on `localhost:8081`. 