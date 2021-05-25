import functools
import logging

class RetryOnException:
    def __init__(self, retries):
        self._retries = retries
        self.logger = logging.getLogger(__name__)

    def __call__(self, function):
        functools.update_wrapper(self, function)

        def wrapper(*args, **kwargs):
            self.logger.info(f"Retries: {self._retries}")
            while self._retries != 0:
                try:
                    return function(*args, **kwargs)
                except Exception as err:
                    self.logger.info(f"Retries: {err}")
                    self._retries -= 1
                    self._raise_on_condition(self._retries, err)

        return wrapper

    def _raise_on_condition(self, retries, exception):
        if retries == 0:
            raise exception
        else:
            self.logger.info(f"Retries: {retries}")