---
layout: default
title: Scraping
nav_order: 3
has_children: true
---

# Scraping store websites
Data is searched in 2 different stores as of the writing of this documentation: Target and Walmart.

## Accessing/requesting the scrapers
Scrapers may be accessed via a REST API created with a Flask package. REST endpoints are the same, differentiating
between what data wants to be scraped (stores or products) and the supplier. Each endpoint allows receives HTTP 
`PUT` requests. The table below describes the parameters that can be sent to the endpoint.

| Parameter name  | Accepted types    | Notes           |
|:----------------|:------------------|:----------------|
| store           | int, String       | Can be sent as either a String or an integer.           |
| product         | String            | Product search term to look for. Must be a String type  |

Multiple of the same parameters may be sent as long as the proper URL encoding is used. All the following
examples may be used as valid HTTP `PUT` requests:

Example 1:
```
POST http://localhost:8480/target/products?product='apples'&product='oranges'&store=1234&store=686
```

Example 2:
```
POST http://localhost:8480/target/products?product='apples'&store=1234
```

Example 3:
```
POST http://localhost:8480/target/products?product='apples'&product='white%20chocolate'&store='1234'
```

## How it works
When an endpoint receives a request, then "forwards" the request to the store website that it should go to. The URL of
the store website was determined manually by checking Network tabs of a browser when search terms were entered into the
website's search bar. Because the store URL obviously differs between stores, it takes some time to figure out what URL
to send a request to as well as the format of the request and the format of the response.

After the response is received from the grocery store's remote server, the value is sent to a Kafka topic to be parsed
by another service. Handing off the parsing to another service ensures that the service does not hang or spend too much
time parsing the data. After all product/store combinations have finished, the application will return response codes in
JSON format for whether the search succeeded or not. An example response would look like the following:

```
{ 
    1234: {
        "apples": "OK",
        "oranges": "OK",
        "white chocolate": "ERROR"
    },
    686: {
        "apples": "OK",
        "oranges": "OK",
        "white chocolate": "OK"
    }
}
```


## Target test:
- Store: 1273
- Store: 686

## Walmart test:
- Store: 3785

## Publix test
- Store: 1746
