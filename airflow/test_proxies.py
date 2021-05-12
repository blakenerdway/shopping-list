from concurrent.futures.thread import ThreadPoolExecutor
import logging
# logging.basicConfig(level=logging.NOTSET)

from proxy.proxy_scraper import ProxyPoolScraper
from proxy.proxy_validator import ProxyPoolValidator

if __name__ == '__main__':
    proxy_scraper = ProxyPoolScraper("https://www.us-proxy.org/")
    proxy_validator = ProxyPoolValidator("https://google.com")
    proxy_stream = proxy_scraper.get_proxy_stream(50)

    with ThreadPoolExecutor(max_workers=50) as executor:
        results = executor.map(
            proxy_validator.validate_proxy, proxy_stream
        )
        valid_proxies = filter(lambda x: x.is_valid is True, results)
        sorted_valid_proxies = sorted(
            valid_proxies, key=lambda x: x.health, reverse=True
        )

    for proxy in sorted_valid_proxies:
        print(proxy)