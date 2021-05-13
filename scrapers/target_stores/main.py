import requests


def store_target_stores(request):
    pass


def parse_target_stores(json_arr):
    stores = []
    for json_obj in json_arr:
        locations_arr = json_obj["locations"]
        for location_obj in locations_arr:
            location_data = {"location_id": location_obj["location_id"],
                             "address_line1": location_obj["address"]["address_line1"],
                             "city": location_obj["address"]["city"], "state": location_obj["address"]["state"],
                             "zip_code": location_obj["address"]["postal_code"],
                             "phone_number": location_obj["contact_information"]["telephone_number"],
                             "latitude": location_obj["geographic_specifications"]["latitude"],
                             "longitude": location_obj["geographic_specifications"]["longitude"],
                             "store": "Target"}
            stores.append(location_data)
    print(stores)
    return stores


def gcf_parse_target_stores(request):
    request_json = request.get_json()
    if request.args and 'json_arr' in request.args:
        json_arr = request.args.get('json_arr')
    elif request_json and 'json_arr' in request_json:
        json_arr = request_json['json_arr']
    else:
        return "Store json must be included in request"
    return {'stores': parse_target_stores(json_arr)}


def run_request(url, headers, parameters, proxies=None):
    val = requests.get(url, params=parameters, headers=headers, proxies=proxies)
    print(val.raw.version)
    try:
        json = val.json()
    except Exception as e:
        if val.status_code == 408:
            json = run_request(url, headers, parameters)
        else:
            print(f'{val.status_code}-{val.reason}')
            return []
    return json


def gcf_request_target_stores(request):
    request_json = request.get_json()
    if request.args and 'zip' in request.args:
        zip = request.args.get('zip')
    elif request_json and 'zip' in request_json:
        zip = request_json['zip']
    else:
        return "Zip must be included in request"

    if request.args and 'headers' in request.args:
        headers = request.args.get('headers')
    elif request_json and 'headers' in request_json:
        headers = request_json['headers']
    else:
        return "Headers must be included in request"

    if request.args and 'proxies' in request.args:
        proxies = request.args.get('proxies')
    elif request_json and 'proxies' in request_json:
        proxies = request_json['proxies']
    else:
        proxies = None
        print("Not using any proxies")

    url = f"https://redsky.target.com/v3/stores/nearby/{zip}"
    parameters = {"key": "ff457966e64d5e877fdbad070f276d18ecec4a01", "limit": "20", "within": 100, "unit": "miles"}

    print(f"Querying {url} with default parameters")
    return {'json_stores': run_request(url, headers, parameters, proxies)}


if __name__ == '__main__':
    import threading
    headers = {"Accept": "application/json", "Accept-Language": "en-US", "Connection": "keep-alive"}
    parameters = {"key": "ff457966e64d5e877fdbad070f276d18ecec4a01", "limit": "20", "within": 100, "unit": "miles"}
    for i in range(1001, 99951):
        json = run_request(f"https://redsky.target.com/v3/stores/nearby/{i}", headers, parameters)
        stores = parse_target_stores(json)
        # threading.Thread(target=run_request, args=(f"https://redsky.target.com/v3/stores/nearby/{i}",)).start()
