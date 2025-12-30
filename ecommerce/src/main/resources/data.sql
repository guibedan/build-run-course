
INSERT INTO tb_products (product_id, product_name, price)
VALUES
    (1, "Computer", 4500.50),
    (2, "SmartPhone", 999.99),
    (3, "Mouse", 22.50)
ON DUPLICATE KEY UPDATE product_id = product_id;

INSERT INTO tb_tags (tag_id, name)
VALUES
    (1, "Eletronics"),
    (2, "Apple"),
    (3, "Home")
ON DUPLICATE KEY UPDATE tag_id = tag_id;

INSERT IGNORE INTO tb_products_tags (product_id, tag_id)
VALUES
    (1, 1),
    (2, 1),
    (2, 2),
    (3, 1);
