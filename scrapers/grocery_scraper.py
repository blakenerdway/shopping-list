from multiprocessing.pool import Pool


class GroceryScraper:
    def __init__(self, kafka_producer):
        self.kafka_producer = kafka_producer

    def __generate_request(self, store, product):
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
            for store in stores:
                for product in products:
                    print(f'{store}: {product}')
                    pool.apply_async(self.__generate_request, (store, product))
            pool.close()
            pool.join()
        else:
            for store in stores:
                for product in products:
                    self.__generate_request(product, store)

        self.kafka_producer.flush()
