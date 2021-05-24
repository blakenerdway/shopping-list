from dataclasses import dataclass


@dataclass
class ProxyInfo:
    ip_address: str
    port: int
    https: bool
    health: float