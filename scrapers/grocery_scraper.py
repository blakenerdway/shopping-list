from multiprocessing.pool import Pool
import multiprocessing
from multiprocessing.pool import ThreadPool


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
        total = len(stores) * len(products) / 50
        pool_size = int(total)
        if pool_size > 0:
            pool = ThreadPool(processes=pool_size)
            shared_dict = multiprocessing.Manager().dict()
            processes = []
            for store in stores:
                for product in products:
                    print(f'{store}: {product}')
                    result = pool.apply_async(self._generate_request, (store, product, shared_dict))
                    processes.append(result)

            [result.wait() for result in processes]
            pool.close()
            pool.join()

            ret_val = dict(shared_dict)
            print(ret_val)
        else:
            ret_val = {}
            for store in stores:
                for product in products:
                    if store not in ret_val:
                        ret_val[store] = {}
                    result = self._generate_request(store, product, None)
                    ret_val[store][product] = result

        self.kafka_producer.flush()

        return ret_val
