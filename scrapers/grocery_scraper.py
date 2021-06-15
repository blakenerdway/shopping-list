from multiprocessing.pool import Pool
import multiprocessing


class GroceryScraper:
    def __init__(self, kafka_producer):
        self.kafka_producer = kafka_producer

    def _generate_request(self, store, product, ret_dict):
        pass

    """
    Get the products from a website. Use multiprocessing if the combination of stores * products is large enough.
    50 items is the default before it starts using a pool. This is because pooling is not performant enough for small 
    batches.
    """
    def scrape_products(self, stores, products):
        pool_size = int(len(stores) * len(products) / 50)
        if pool_size > 0:
            pool = Pool(pool_size)
            dict = multiprocessing.Manager().dict()
            for store in stores:
                for product in products:
                    print(f'{store}: {product}')
                    pool.apply(self._generate_request, (store, product, dict))
            pool.close()
            pool.join()
            ret_val = dict
            print(ret_val)
        else:
            ret_val = {}
            for store in stores:
                for product in products:
                    if store not in ret_val:
                        ret_val[store] = {}
                    result = self._generate_request(product, store, None)
                    ret_val[store][product] = result

        self.kafka_producer.flush()

        return ret_val
