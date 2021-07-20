---
layout: default
title: MySQL connections
nav_order: 1
parent: MySQL
grand_parent: Storage
---

# Connections to MySQL
This document outlines connecting to MySQL locally, as well as describing what containers currently have connections to
the MySQL database.

## Credentials
Username: `admin`
Password: `password!`

## Local connections
You may use some GUI software to connect to the MySQL instance. If you want to connect to the docker container itself
and then to the database, you may run the following command:

```
$ docker exec -it mysql mysql -u root -p shopping_list
```

Enter the password when prompted

## Containers
Both Airflow and Beam both have connections to MySQL.

### Airflow
Airflow creates a connection entry on startup to connect to the MySQL instance. In the future, it will query the database
to determine what items to search for and how often as well as the number of results to return. It will eventually also query
the database to determine what stores to search for items in.

### Beam
Each pipeline created in Beam uses a JDBC connector to push data into the database tables. The connection is managed and handled
automatically in 