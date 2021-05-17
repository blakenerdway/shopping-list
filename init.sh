#!/bin/bash

docker network create shopping-list-net

docker run --name shopping-list -e MYSQL_ROOT_PASSWORD=password! \
	--network=shopping-list-net \
	--mount type=bind,src=${pwd}`/mysql/,dst=/docker-entrypoint-initdb.d/ \
	-d mysql:8

# Start the kafka cluster
cd kafka-docker
docker-compose up -d

