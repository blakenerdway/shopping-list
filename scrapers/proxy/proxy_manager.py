from time import sleep
from multiprocessing import Process
import mysql.connector
from .proxy_info import ProxyInfo
import datetime


class ProxyManager:
    def __init__(self, config):
        super().__init__()
        self.db_conf = config
        self.proxies = []
        p = Process(self.query_for_proxies())
        p.daemon = True
        p.start()

    def get_proxy(self):
        pass

    def query_for_proxies(self):
        query = ("SELECT ip, port, https, health FROM proxy WHERE health > .6 AND update_time BETWEEN DATE_SUB(NOW(), INTERVAL 5 MINUTE)")
        while True:
            cnx = mysql.connector.connect(**self.db_conf)
            cursor = cnx.cursor()
            cursor.execute(query)
            for (ip, port, https, health) in cursor:
                self.proxies.append(ProxyInfo(ip, port, https, health))
                pass
            cursor.close()
            cnx.close()
            sleep(20)
