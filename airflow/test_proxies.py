from concurrent.futures.thread import ThreadPoolExecutor
from fp.fp import FreeProxy

if __name__ == '__main__':
    proxy = FreeProxy(country_id=['US', 'CA'], rand=True).get()
    if proxy is not None:
        print(proxy)