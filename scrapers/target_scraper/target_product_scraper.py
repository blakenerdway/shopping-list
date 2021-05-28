from multiprocessing.pool import Pool
# Use uuid to generate a random visitor_id
import uuid
from util import run_request
from grocery_scraper import GroceryScraper


class TargetProductScraper(GroceryScraper):
    def __init__(self, kafka_producer):
        super().__init__(kafka_producer)

    def _generate_request(self, store, product, ret_queue):
        params = {"key": "ff457966e64d5e877fdbad070f276d18ecec4a01",
                  "channel": "WEB",
                  "count": "10",
                  "keyword": product,
                  # Need non-null value here, but it should not matter what
                  "page": " ",
                  "pricing_store_id": store,
                  # use a randomized hexadecimal uppercase string as the visitor ID
                  "visitor_id": uuid.uuid1().hex.upper()}

        # TODO: randomize the headers
        headers = {"Accept": "application/json", "Accept-Language": "en-US", "Connection": "keep-alive"}

        url = "https://redsky.target.com/redsky_aggregations/v1/web/plp_search_v1"

        res = run_request(url, headers, params)
        product_send = {product: res}
        ret_val = 'OK'
        if not res:
            ret_val = 'ERROR'

        self.kafka_producer.send('target_scraper.products', product_send)

        if ret_queue is not None:
            if store not in ret_queue:
                ret_queue[store] = {}
            ret_queue[store][product] = ret_val

        return ret_val
