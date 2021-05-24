CREATE DATABASE IF NOT EXISTS shopping_list;
USE shopping_list;

CREATE TABLE `proxy` (
  `ip` INT UNSIGNED,
  `port` INT UNSIGNED,
  `https` bool,
  `health` float4,
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ip`, `port`)
);

