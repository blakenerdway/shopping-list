CREATE DATABASE IF NOT EXISTS shopping_list;
USE shopping_list;

CREATE TABLE `products` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `brand` varchar(255),
  `size` varchar(255),
  `price` float4,
  `supplier_id` int,
  `note_id` int,
  `url` varchar(255)
);

CREATE TABLE `stores` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `store` varchar(255),
  `address_line1` varchar(255),
  `city` varchar(255),
  `state` varchar(255),
  `zip_code` varchar(255),
  `phone_number` varchar(255),
  `latitude` float4,
  `longitude` float4
);

CREATE TABLE `notes` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `note` varchar(255)
);

CREATE TABLE `searches` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `term` varchar(255)
);

CREATE TABLE `product_searches` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `product_id` int,
  `term_id` int
);

ALTER TABLE `products` ADD FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`);

ALTER TABLE `products` ADD FOREIGN KEY (`supplier_id`) REFERENCES `products` (`id`);

ALTER TABLE `product_searches` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);
ALTER TABLE `product_searches` ADD FOREIGN KEY (`term_id`) REFERENCES `searches` (`id`);