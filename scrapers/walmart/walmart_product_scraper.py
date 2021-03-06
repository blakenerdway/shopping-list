import multiprocessing
from multiprocessing.pool import Pool
from util import run_request
from grocery_scraper import GroceryScraper
from fake_headers import Headers


class WalmartProductScraper(GroceryScraper):
    def __init__(self, kafka_producer):
        super().__init__(kafka_producer)
        self.header = Headers(headers=True)

    def _generate_request(self, store, product, ret_dict):
        params = {"count": 10,
                  "offset": 0,
                  "page": 1,
                  "storeId": store,
                  "query": product}

        headers = self.header.generate()
        headers["Accept"] = "application/json"

        url = "https://www.walmart.com/grocery/v4/api/products/search"

        res = run_request(url, headers, params)
        product_send = {"store": store, "product": product, "result": res}

        ret_val = 'OK'
        if not res:
            ret_val = 'ERROR'

        self.kafka_producer.send('walmart.products', product_send)

        if ret_dict is not None:
            if store not in ret_dict:
                ret_dict[store] = {product: ret_val}
            else:
                tmp_dict = ret_dict[store]
                tmp_dict[product] = ret_val
                ret_dict[store] = tmp_dict

        return ret_val
