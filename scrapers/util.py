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