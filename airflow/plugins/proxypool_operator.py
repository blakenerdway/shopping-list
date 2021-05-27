from concurrent.futures import ThreadPoolExecutor

from retry.retry_on_exception import RetryOnException as retry
from proxy.proxy_scraper import ProxyPoolScraper
from proxy.proxy_validator import ProxyPoolValidator

from airflow.models.baseoperator import BaseOperator
from airflow.utils.decorators import apply_defaults
import logging


class ProxyPoolOperator(BaseOperator):

    @apply_defaults
    def __init__(
            self,
            proxy_webpage,
            number_of_proxies,
            testing_url,
            max_workers,
            *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.proxy_webpage = proxy_webpage
        self.testing_url = testing_url
        self.number_of_proxies = number_of_proxies
        self.max_workers = max_workers

    @retry(5)
    def execute(self, context):
        logging.debug("Creating scraper")
        print("Creating scraper")
        proxy_scraper = ProxyPoolScraper(self.proxy_webpage)
        logging.debug("Creating validator")
        proxy_validator = ProxyPoolValidator(self.testing_url)
        proxy_stream = proxy_scraper.get_proxy_stream(self.number_of_proxies)

        with ThreadPoolExecutor(max_workers=self.max_workers) as executor:
            results = executor.map(
                proxy_validator.validate_proxy, proxy_stream
            )
            logging.debug("Filter valid proxies")
            valid_proxies = filter(lambda x: x.is_valid is True, results)
            sorted_valid_proxies = sorted(valid_proxies, key=lambda x: x.health, reverse=True)

        logging.debug("Finished proxy pool scraping. Pushing valid proxies")
        context['ti'].xcom_push(key='proxies', value=sorted_valid_proxies)