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
    params = {"count": 60,
              "offset": 0,
              "page": 1,
              "storeId": store,
              "query": product}

    headers = {"Accept": "application/json",
               "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:87.0) Gecko/20100101 Firefox/87.0" }

    url = "https://www.walmart.com/grocery/v4/api/products/search"

    print(run_request(url, headers, params))


if __name__ == '__main__':
    request_product("flour", 3785)
