CREATE DATABASE IF NOT EXISTS shopping-list;
USE shopping-list;

CREATE TABLE `products` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `brand` varchar(255),
  `size` varchar(255),
  `price` float4,
  `supplier` varchar(255),
  `supplier_id` int,
  `note_id` varchar(255) NOT NULL,
  `url` varchar(255)
);

CREATE TABLE `stores` (
  `id` int AUTO_INCREMENT,
  `store` varchar(255),
  `address_line1` varchar(255),
  `city` varchar(255),
  `state` varchar(255),
  `zip_code` varchar(255),
  `phone_number` varchar(255),
  `latitude` float4,
  `longitude` float4,
  PRIMARY KEY (`id`, `store`)
);

CREATE TABLE `product_tags_linking` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `product_id` int,
  `tag_id` int
);

CREATE TABLE `tag` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255)
);

CREATE TABLE `notes` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `note` varchar(255)
);

ALTER TABLE `notes` ADD FOREIGN KEY (`id`) REFERENCES `products` (`note_id`);

ALTER TABLE `stores` ADD FOREIGN KEY (`id`, `store`) REFERENCES `products` (`supplier_id`, `supplier`);

ALTER TABLE `product_tags_linking` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `product_tags_linking` ADD FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`);

