import requests

if __name__ == '__main__':
    payload = {'product': ['ice cream', 'red apples', 'oranges'], 'store': [1273, 686]}
    res = requests.put("http://localhost:8480/target/products", params=payload)
    print(res.content.decode('utf-8'))