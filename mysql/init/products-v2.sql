CREATE DATABASE IF NOT EXISTS shopping_list;
USE shopping_list;

CREATE TABLE brands(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    created_time TIMESTAMP default NOW(),
    updated_time TIMESTAMP default NOW() on update NOW()
 );

CREATE TABLE products(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    brand_id int,
    created_time TIMESTAMP default NOW(),
    updated_time TIMESTAMP default NOW() on update NOW(),
    FOREIGN KEY (brand_id) references brands(id)
);

CREATE TABLE product_info (
  id int PRIMARY KEY AUTO_INCREMENT,
  product_id int,
  store_id int,
  supplier_name varchar(255),
  size varchar(255),
  price float,
  note_id int,
  created_time TIMESTAMP default NOW(),
  updated_time TIMESTAMP default NOW() on update NOW(),
  FOREIGN KEY (product_id) REFERENCES products(id),
  FOREIGN KEY (store_id,supplier_name) REFERENCES stores(id,supplier_name),
  FOREIGN KEY (note_id) REFERENCES notes(id)
);

CREATE TABLE stores (
  id int,
  supplier_name varchar(255),
  address_line1 varchar(255),
  city varchar(255),
  state varchar(255),
  zip_code varchar(255),
  phone_number varchar(255),
  coordinate point,
  created_time  TIMESTAMP default NOW(),
  updated_time TIMESTAMP default NOW() on update NOW(),
  PRIMARY KEY (id, supplier_name)
);

CREATE TABLE notes (
  id int PRIMARY KEY AUTO_INCREMENT,
  created_time  TIMESTAMP default NOW(),
  note varchar(255)
);

CREATE TABLE searches (
  id int PRIMARY KEY AUTO_INCREMENT,
  created_time  TIMESTAMP default NOW(),
  term varchar(255)
);

CREATE TABLE product_searches (
  id int PRIMARY KEY AUTO_INCREMENT,
  product_id int,
  term_id int,
  created_time  TIMESTAMP default current_timestamp,
  FOREIGN KEY (product_id) REFERENCES products (id),
  FOREIGN KEY (term_id) REFERENCES searches (id)
);
