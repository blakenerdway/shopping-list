---
layout: default
title: Quickstart
nav_order: 2
---

# Quick start
## Downloading
Download the full source-code can be found on the [Github repo](https://github.com/blakenerdway/shopping-list)

## Setup
A simple setup is required to give you some example data. This data is stored separately from the repository, available for
public use. Using some example data allows us to jump straight into data analysis.

A script has been created in `$SHOPPING_LIST_HOME/` to pull the data from the remote repository

Run the setup script (this only needs to be done once)

**Windows**
```
C:\Users\default\shopping-list> setup
``` 

**Linux**
```
$ ./setup.sh
```

Data has been downloaded and stored in the MySQL database and the Druid database. The MySQL database is used as a transactional
database, good for our front-facing REST API to query for items, and for our pipeline to update items as needed.

The Druid database is used as an analytics database, good for data queries that would take a while with multiple complex joins.

## Exploring the data
### MySQL
You can explore the dataset in normalized form in the MySQL container running on `localhost:3306`. Take a look at the different
tables and relations listed in the [data schema](/shopping-list/storage/mysql/schema) page.

### Druid
The Druid database provides some transformed data better used for analysis. The dataset is still quite small, but it provides
some interesting uses. You can look at the Druid console at `localhost:8888`. 
For more information, check out the [page](/shopping-list/storage/druid) for Druid.