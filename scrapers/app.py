import json

from kafka import KafkaProducer
from flask import Flask, make_response, request

# from amazon.amazon_product_scraper import AmazonProductScraper
from publix.publix_sale_scraper import PublixSaleScraper
from target.target_product_scraper import TargetProductScraper
from walmart.walmart_product_scraper import WalmartProductScraper

app = Flask(__name__)
producer = KafkaProducer(bootstrap_servers=['localhost:9092'], value_serializer=lambda v: json.dumps(v).encode('utf-8'))
target_product_scraper = TargetProductScraper(kafka_producer=producer)
walmart_product_scraper = WalmartProductScraper(kafka_producer=producer)
publix_scraper = PublixSaleScraper(kafka_producer=producer)
# amazon_product_scraper = AmazonProductScraper(kafka_producer=producer)


@app.route('/target/products', methods=['PUT'])
def get_target_products():
    stores = request.args.getlist("store")
    products = request.args.getlist("product")
    target_product_scraper.scrape_products(stores, products)

    return make_response(json.dumps({'success': True}), 200)


@app.route('/walmart/products', methods=['PUT'])
def get_walmart_products():
    stores = request.args.getlist("store")
    products = request.args.getlist("product")
    walmart_product_scraper.scrape_products(stores, products)

    return make_response(json.dumps({'success': True}), 200)


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
