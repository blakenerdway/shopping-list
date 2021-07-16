CREATE DATABASE IF NOT EXISTS shopping_list;
USE shopping_list;

CREATE TABLE brands
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    name         varchar(255),
    created_time TIMESTAMP default NOW(),
    updated_time TIMESTAMP default NOW() on update NOW()
);

CREATE TABLE products
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    name         varchar(255),
    brand_id     int,
    created_time TIMESTAMP default NOW(),
    updated_time TIMESTAMP default NOW() on update NOW(),
    FOREIGN KEY (brand_id) references brands (id)
);

CREATE TABLE notes
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    created_time TIMESTAMP default NOW(),
    note         varchar(255)
);

CREATE TABLE stores
(
    id            int,
    supplier_name varchar(255),
    address_line1 varchar(255),
    city          varchar(255),
    state         varchar(255),
    zip_code      varchar(255),
    phone_number  varchar(255),
    coordinate    point,
    created_time  TIMESTAMP default NOW(),
    updated_time  TIMESTAMP default NOW() on update NOW(),
    PRIMARY KEY (id, supplier_name)
);

CREATE TABLE product_info
(
    id            int PRIMARY KEY AUTO_INCREMENT,
    product_id    int,
    store_id      int,
    supplier_name varchar(255),
    price         float,
    note_id       int,
    created_time  TIMESTAMP default NOW(),
    updated_time  TIMESTAMP default NOW() on update NOW(),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (store_id, supplier_name) REFERENCES stores (id, supplier_name),
    FOREIGN KEY (note_id) REFERENCES notes (id)
);


CREATE TABLE searches
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    created_time TIMESTAMP default NOW(),
    term         varchar(255)
);

CREATE TABLE product_searches
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    product_id   int,
    term_id      int,
    created_time TIMESTAMP default current_timestamp,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (term_id) REFERENCES searches (id)
);


ALTER TABLE `product_info`
    ADD CONSTRAINT `PRODUCT_INFO_IDENTIFIERS` UNIQUE (product_id, store_id, supplier_name);

CREATE PROCEDURE insert_ignore_searchTerm(IN searchTerm varchar(255))
begin
    INSERT INTO searches(term)
    SELECT searchTerm
    FROM DUAL
    WHERE NOT EXISTS(SELECT * FROM searches WHERE term = searchTerm LIMIT 1);
end;

CREATE PROCEDURE insert_ignore_brand(IN brand varchar(255))
begin
    INSERT INTO brands(name) SELECT brand FROM DUAL WHERE NOT EXISTS(SELECT * FROM brands WHERE name = brand LIMIT 1);
end;

CREATE PROCEDURE insert_ignore_product_name(IN product_name varchar(255), IN brand varchar(255))
begin
    INSERT INTO products(name, brand_id)
    SELECT product_name, (SELECT brands.id FROM brands WHERE name = brand)
    FROM DUAL
    WHERE NOT EXISTS(SELECT * FROM products WHERE product_name = name LIMIT 1);
end;

CREATE PROCEDURE insert_ignore_store_entry(IN supplier varchar(255), IN storeID varchar(255))
begin
    INSERT INTO stores(supplier_name, id)
    SELECT supplier, storeID
    FROM DUAL
    WHERE NOT EXISTS(SELECT * from stores WHERE id = storeID AND supplier_name = supplier LIMIT 1);
end;

CREATE PROCEDURE insert_ignore_product_search(IN productName varchar(255), IN searchTerm varchar(255))
begin
    INSERT INTO product_searches(product_id, term_id)
    WITH cte (p_id, p_name, s_id, s_term) AS (
        SELECT products.id, products.name, searches.id, searches.term
        FROM products
                 CROSS JOIN searches
        WHERE products.name = productName AND searches.term = searchTerm)
    SELECT cte.p_id, cte.s_id FROM cte
    WHERE NOT EXISTS(SELECT * FROM product_searches WHERE product_id = cte.p_id AND term_id = cte.s_id LIMIT 1);
end;

CREATE PROCEDURE insert_update_product_info(IN productName varchar(255), IN supplier varchar(255), IN storeID varchar(255), IN new_price float)
begin
    # Insert/update product_info
    INSERT INTO product_info(product_id, store_id, supplier_name, price)
    WITH cte(p_id, store_id, supplier_name) AS (
        SELECT products.id, stores.id, stores.supplier_name FROM products CROSS JOIN stores
        WHERE name = productName AND stores.id = storeID AND stores.supplier_name = supplier)
    SELECT cte.p_id, cte.store_id, cte.supplier_name, (SELECT new_price FROM dual) FROM cte
    ON DUPLICATE KEY UPDATE price = new_price;
end;

CREATE PROCEDURE insert_product(IN searchTerm varchar(255), IN storeID int, IN productName varchar(255),
                                IN brand varchar(255), IN supplier varchar(255), IN price float)
begin
    # Insert the search term
    CALL insert_ignore_searchTerm(searchTerm);

    # Insert the brand
    CALL insert_ignore_brand(brand);

    # Insert the product line item
    CALL insert_ignore_product_name(productName, brand);

    # Insert store information
    CALL insert_ignore_store_entry(supplier, storeID);

    # Insert product search linking table
    CALL insert_ignore_product_search(productName, searchTerm);

    CALL insert_update_product_info(productName, supplier, storeID, price);
end;
