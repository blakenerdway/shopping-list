from multiprocessing.pool import Pool
from util import run_request
from grocery_scraper import GroceryScraper


class PublixSaleScraper(GroceryScraper):
    def __init__(self, kafka_producer):
        super().__init__(kafka_producer)

    def _generate_request(self, store, **kwargs):
        params = {"page": 1}

        headers = {"Accept": "application/json,text/plain,*/*",
                   "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:87.0) Gecko/20100101 Firefox/87.0",
                   "Connection": "keep-alive"}

        headers["Host"] = "services.publix.com"
        headers["PublixStore"] = store

        url = "https://services.publix.com/api/v4/savings"

        res = run_request(url, headers, params)
        print(res)
        self.kafka_producer.send('publix.sales', res)