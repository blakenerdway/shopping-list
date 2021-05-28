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

    """
    Get the products from walmart website. Use multiprocessing if the combination of stores * products is large enough.
    50 items is the default before it starts using a pool. This is because pooling is not performant enough for small 
    batches.
    """
    def scrape_products(self, stores, products=None):
        pool_size = int(len(stores) / 50)
        if pool_size > 0:
            pool = Pool(pool_size)
            for store in stores:
                pool.apply_async(self.__generate_request, (store))
            pool.close()
            pool.join()
        else:
            for store in stores:
                self.__generate_request(store)

        self.kafka_producer.flush()