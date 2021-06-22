CREATE PROCEDURE insert_productinfo(IN searchTerm varchar(255), IN storeID int, IN productName varchar(255),
                                    IN brand varchar(255), IN supplier varchar(255), IN price float)
begin
    # Insert the search term
    INSERT INTO searches(term)
    SELECT searchTerm
    FROM DUAL
    WHERE NOT EXISTS(SELECT * FROM searches WHERE term = searchTerm LIMIT 1);
    INSERT INTO brands(name) SELECT brand FROM DUAL WHERE NOT EXISTS(SELECT * FROM brands WHERE name = brand LIMIT 1);

    # Insert the product line item
    INSERT INTO products(name, brand_id)
    SELECT productName, (SELECT brands.id FROM brands WHERE name = brand)
    FROM DUAL
    WHERE NOT EXISTS(SELECT * FROM products WHERE name = productName LIMIT 1);

    # Insert store information
    INSERT INTO stores(supplier_name, id)
    SELECT supplier, storeID
    FROM DUAL
    WHERE NOT EXISTS(SELECT * from stores WHERE id = storeID AND supplier_name = supplier LIMIT 1);

    # Insert product search linking table
    INSERT INTO product_searches(product_id, term_id)
    WITH prodCTE AS (SELECT products.id as product_id FROM products WHERE products.name = productName),
         searchCTE AS (SELECT searches.id as search_id FROM searches WHERE searches.term = searchTerm)
    SELECT  prodCTE.product_id, searchCTE.search_id
    FROM dual
    WHERE NOT EXISTS(SELECT * FROM product_searches WHERE prodCTE.product_id = product_searches.product_id AND searchCTE.search_id = product_searches.term_id  LIMIT 1);


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