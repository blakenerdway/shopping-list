import requests


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
    request_product("red apples", 686)
