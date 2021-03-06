import multiprocessing
from multiprocessing.pool import Pool
# Use uuid to generate a random visitor_id
import uuid
from util import run_request
from grocery_scraper import GroceryScraper
from fake_headers import Headers


class TargetProductScraper(GroceryScraper):
    def __init__(self, kafka_producer):
        super().__init__(kafka_producer)
        self.header = Headers(headers=True)

    def _generate_request(self, store, product, ret_dict):
        params = {"key": "ff457966e64d5e877fdbad070f276d18ecec4a01",
                  "channel": "WEB",
                  "count": "10",
                  "keyword": product,
                  # Need non-null value here, but it should not matter what
                  "page": f"s/{product}",
                  "pricing_store_id": int(store),
                  # use a randomized hexadecimal uppercase string as the visitor ID
                  "visitor_id": uuid.uuid1().hex.upper()}

        headers = self.header.generate()
        headers["Accept"] = "application/json"

        url = "https://redsky.target.com/redsky_aggregations/v1/web/plp_search_v1"

        res = run_request(url, headers, params)
        product_send = {"store": store, "product": product, "result": res}
        ret_val = 'OK'
        if not res:
            ret_val = 'ERROR'

        self.kafka_producer.send('target.products', product_send)

        if ret_dict is not None:
            if store not in ret_dict:
                ret_dict[store] = {product: ret_val}
            else:
                tmp_dict = ret_dict[store]
                tmp_dict[product] = ret_val
                ret_dict[store] = tmp_dict

        return ret_val
