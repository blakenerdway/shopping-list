---
layout: default
title: Communication
nav_order: 5
---
# Microservice communication
Data is passed between different microservices via Kafka. While the scraper containers do use REST Endpoints to
receive communication (i.e., not using Kafka), this example of data movement is not within the scope of this page.
To learn more about how the scrapers work, check out the [scraper page](/shopping-list/scraping)

## Kafka setup
Kafka and Zookeeper are both generated in the same docker-compose file. A single Kafka broker is used to facilitate
communicate. Kafka brokers and clients communicate via PLAINTEXT (no SSL). Clients outside the Docker network may connect
via port 9092 while clients within the Docker network may connect via port 9093. 

### Kafka message timestamps
Kafka is set up to use `LogAppendTime`, using the time at which the broker inserted the record, rather than the time
at which the client generated the message. Because our the is not timestamp-driven or event-type data, there is really
no use in ensuring data comes in a correct order via timestamps. 

## Kafka topics
Kafka topics are created in the simplest possible way

| Supplier  | Product topic     | Store topic     |
|:----------|:------------------|:----------------|
| Target    | target.products   | target.stores   |
| Walmart   | walmart.products  | walmart.stores  |
