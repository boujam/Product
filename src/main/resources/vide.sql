USE testpro3;

/* 
TRUNCATE TABLE product;
*/

DELETE FROM product;

DBCC CHECKIDENT ('product', RESEED, 0);

/*
INSERT INTO product
(
    name,
    price,
    description,
    isbn
)
VALUES
(
    'Test',
    10.00,
    'Livre de test',
    '9790000000001'
);

SELECT *
FROM product;
*/

/*
SELECT * FROM product

*/