from multiprocessing.pool import Pool
from util import run_request
from grocery_scraper import GroceryScraper


class WalmartProductScraper(GroceryScraper):
    def __init__(self, kafka_producer):
        super().__init__(kafka_producer)

    def _generate_request(self, store, product, ret_queue):
        params = {"count": 10,
                  "offset": 0,
                  "page": 1,
                  "storeId": store,
                  "query": product}

        headers = {"Accept": "application/json",
                   "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:87.0) Gecko/20100101 Firefox/87.0"}

        url = "https://www.walmart.com/grocery/v4/api/products/search"

        res = run_request(url, headers, params)
        product_send = {product: res}
        ret_val = 'OK'
        if not res:
            ret_val = 'ERROR'

        self.kafka_producer.send('walmart.products', product_send)
        if ret_queue is not None:
            if store not in ret_queue:
                ret_queue[store] = {}
            ret_queue[store][product] = ret_val

        return ret_val
