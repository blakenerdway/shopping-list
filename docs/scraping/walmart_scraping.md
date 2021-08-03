---
layout: default
title: Walmart Scraping
nav_order: 2
parent: Scraping
---

# Product scraping
## URL
A `GET` request is used to get items from the following Walmart product URL: 
`https://www.walmart.com/grocery/v4/api/products/search`.

## Parameters
Certain parameters are required for returning the results of the website. The following parameters are what were
determined to be required or important for returning proper values

| Parameter name   | Parameter type | Notes                                                                                                        |
|:-----------------|:---------------|:-------------------------------------------------------------------------------------------------------------|
| count            | int            | This key is one that never changed when requesting items. It's possible that it does change at some point    |
| offset           | int            | An offset from the start to search for. Should be 0                                                          |
| page             | int            | Page to return items from (similar to offset). Should be 1                                                   |
| storeId          | String         | The store to search items in                                                                                 |
| query            | String         | The product to search for                                                                                    |

## Returned Values
After receiving the value from the endpoint, the raw JSON is sent to Kafka.
An example returned value is listed below.