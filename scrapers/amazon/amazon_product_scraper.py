from multiprocessing.pool import Pool
# Use uuid to generate a random visitor_id
from proxy import run_request
from grocery_scraper import GroceryScraper


# class AmazonProductScraper(GroceryScraper):
#     def __init__(self, kafka_producer):
#         super().__init__(kafka_producer)
#
#     def __generate_request(self, product, store):
#         params = {"count": 10,
#                   "offset": 0,
#                   "page": 1,
#                   "storeId": store,
#                   "query": product}
#
#         headers = {"Accept": "application/json",
#                    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:87.0) Gecko/20100101 Firefox/87.0"}
#
#         url = "https://www.walmart.com/grocery/v4/api/products/search"
#
#         res = {product: run_request(url, headers, params)}
#         self.kafka_producer.send('walmart.products', value={product: res})
#         self.kafka_producer.flush()
#
#     """
#     Get the products from walmart website. Use multiprocessing if the combination of stores * products is large enough.
#     50 items is the default before it starts using a pool. This is because pooling is not performant enough for small
#     batches.
#     """
#     def scrape_products(self, stores, products):
#         pool_size = int(len(stores) * len(products) / 50)
#         if pool_size > 0:
#             pool = Pool(pool_size)
#             for store in stores:
#                 for product in products:
#                     print(f'{store}: {product}')
#                     pool.apply_async(self.__generate_request, (product, store))
#         else:
#             for store in stores:
#                 for product in products:
#                     self.__generate_request(product, store)