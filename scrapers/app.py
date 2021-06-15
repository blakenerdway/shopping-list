import json
import os
from kafka import KafkaProducer
from flask import Flask, make_response, request, jsonify
from proxy.proxy_manager import ProxyManager

# from amazon.amazon_product_scraper import AmazonProductScraper
from publix.publix_sale_scraper import PublixSaleScraper
from target_scraper.target_product_scraper import TargetProductScraper
from walmart.walmart_product_scraper import WalmartProductScraper

app = Flask(__name__)
is_docker = os.getenv("DOCKER_INSIDE")
print(is_docker)
if is_docker is not None:
    kafka_host = 'kafka:9093'
else:
    kafka_host = 'localhost:9092'
producer = KafkaProducer(bootstrap_servers=[kafka_host], value_serializer=lambda v: json.dumps(v).encode('utf-8'))
target_product_scraper = TargetProductScraper(kafka_producer=producer)
walmart_product_scraper = WalmartProductScraper(kafka_producer=producer)
publix_scraper = PublixSaleScraper(kafka_producer=producer)

proxy_manager = ProxyManager()


@app.route('/target/products', methods=['PUT'])
def get_target_products():
    stores = request.args.getlist("store")
    products = request.args.getlist("product")
    ret_val = target_product_scraper.scrape_products(stores, products)

    return make_response(jsonify(ret_val), 200)


@app.route('/walmart/products', methods=['PUT'])
def get_walmart_products():
    stores = request.args.getlist("store")
    products = request.args.getlist("product")
    ret_val = walmart_product_scraper.scrape_products(stores, products)

    return make_response(jsonify(ret_val), 200)


@app.route('/publix/products', methods=['PUT'])
def get_publix_sales():
    stores = request.args.getlist("store")
    publix_scraper.scrape_products(stores)

    return make_response(json.dumps({'success': True}), 200)


@app.route('/amazon/products', methods=['PUT'])
def get_amazon_products():
    # stores = request.args.getlist("store")
    # products = request.args.getlist("product")
    # amazon_product_scraper.scrape_products(stores, products)

    return make_response(json.dumps({'success': False}), 404)
