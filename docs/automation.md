---
layout: default
title: Automation
nav_order: 7
---
# Automation
Airflow is used to automatically request our local REST API to search for certain items. This keeps the items’ prices 
updated as well as whether or not the items are in stock at a store. A “cold-start” was used for querying items. 
A list of common searchable grocery items was created at the beginning of the project. This list could be updated based 
on what people query the MySQL database for. A smart method could be for the Airflow job to update certain items more 
frequently based on their usage/searches in the MySQL database.

## Future work
At this point, only a few stores local to my area are searched. These store numbers are hardcoded based on what I found
when researching data formats/URLs to query. Eventually, I would like to search for stores across the US.

## Search items
A JSON file with categories of common items is used to search for items across different website's search functionality.
DAG jobs are split up by categories.


## DAGs
### Grocery scraping
For each store that has had scraping and parsing implemented, a DAG is created. At this point in time, the only stores
that are available to be scraped/parsed are Walmart and Target--resulting in 2 DAGS. The two DAGS are run at separate
times of the day, so as to not bog down the scraper module. The Target DAG is run at midnight daily; Walmart at noon daily. 
The grocery scraping file goes through the [search item list](#search-items) and creates a job for each category.

When a DAG runs, each job takes a list of items and a list of stores (in this case, the stores are hardcoded) and 
creates a POST request to the local scrapers. Note that the domain name for the scrapers is resolved because both Airflow
and the scrapers sit within a Docker container in the same **bridge** network (if it were a **host** network, `localhost`
could be used). Jobs are run sequentially so as to not bog down the scraper. The scraper returns "OK" for each line item per store
that was sent. This could be used to rerun certain items if they did not return "OK".

### Store scraping
Stores can be scraped from websites on a very rare basis. Once a week or once a month may suffice for scraping store
data. DAGs for store scraping are not currently available, but may be in the near future.