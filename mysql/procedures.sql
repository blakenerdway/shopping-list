CREATE DATABASE IF NOT EXISTS shopping_list;
USE shopping_list;

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
    WITH prodCTE AS (SELECT products.id as product_id FROM products WHERE products.name = productName),
         searchCTE AS (SELECT searches.id as search_id FROM searches WHERE searches.term = searchTerm)
    SELECT  prodCTE.product_id, searchCTE.search_id
    FROM dual;
end;

CREATE PROCEDURE insert_update_product_info(IN productName varchar(255), IN supplier varchar(255), IN storeID varchar(255), IN price int)
begin

    # Insert/update product_info
    INSERT INTO product_info(product_id, store_id, supplier_name, price, note_id)
    WITH prodCTE AS (SELECT id as prod_id FROM products WHERE name = productName),
         storeCTE AS (SELECT id as store_id, supplier_name FROM stores WHERE id = storeID AND supplier_name = supplier)
    SELECT (SELECT prod_id FROM prodCTE),
           (SELECT store_id from storeCTE),
           (SELECT supplier_name from storeCTE),
           price,
           NULL
    FROM DUAL
    WHERE NOT EXISTS(SELECT * FROM product_info WHERE prodCTE.prod_id = product_info.id AND storeCTE.store_id = product_info.store_id AND storeCTE.supplier_name = product_info.supplier_name  LIMIT 1);
end;