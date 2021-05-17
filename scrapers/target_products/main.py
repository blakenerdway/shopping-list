import json

import requests
from kafka import KafkaConsumer

def run_request(url, headers, parameters, proxies=None):
    val = requests.get(url, params=parameters, headers=headers, proxies=proxies)
    try:
        json = val.json()
    except Exception as e:
        if val.status_code == 408:
            json = run_request(url, headers, parameters)
        else:
            print(f'{val.status_code}-{val.reason}')
            return []
    return json


def request_product(product, store):
    params = {"key": "ff457966e64d5e877fdbad070f276d18ecec4a01",
              "channel": "WEB",
              "count": "3",
              "keyword":  product,
              "page": f"/s/{product}",
              "pricing_store_id": store,
              "visitor_id": "0175FB1A5AF3020183AEACF1459076D0"}

    headers = {"Accept": "application/json", "Accept-Language": "en-US", "Connection": "keep-alive"}

    url = "https://redsky.target.com/redsky_aggregations/v1/web/plp_search_v1"

    print(run_request(url, headers, params))


if __name__ == '__main__':
    from kafka import KafkaConsumer
    from json import loads

    consumer = KafkaConsumer(
        'test',
        auto_offset_reset='earliest',
        enable_auto_commit=True,
        group_id='my-group-1',
        value_deserializer=lambda m: loads(m.decode('utf-8')),
        bootstrap_servers=['localhost:9092'])

    for m in consumer:
        print(m.value)


    # request_product("red apples", 686)

