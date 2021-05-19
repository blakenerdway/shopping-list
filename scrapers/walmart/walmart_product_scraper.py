from multiprocessing.pool import Pool
# Use uuid to generate a random visitor_id
from util import run_request
from grocery_scraper import GroceryScraper


class WalmartProductScraper(GroceryScraper):
    def __init__(self, kafka_producer):
        super().__init__(kafka_producer)

    def __generate_request(self, store, product):
        params = {"count": 10,
                  "offset": 0,
                  "page": 1,
                  "storeId": store,
                  "query": product}

        headers = {"Accept": "application/json",
                   "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:87.0) Gecko/20100101 Firefox/87.0"}

        url = "https://www.walmart.com/grocery/v4/api/products/search"

        res = {product: run_request(url, headers, params)}
        print(res)
        self.kafka_producer.send('walmart.products', res)
        self.kafka_producer.flush()