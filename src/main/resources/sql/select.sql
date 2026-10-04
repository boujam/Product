/*
SELECT *
FROM product

SELECT *
FROM book

SELECT *
FROM video_game

SELECT *
FROM dvd
*/


/* Tous les produits avec leurs informations spécifiques */
/*
SELECT
    p.id,
    p.name,
    p.price,
    p.description,
    p.product_type,

    b.isbn,
    b.author,
    b.publisher,
    b.number_of_pages,

    vg.developer,
    vg.platform,
    vg.genre,
    vg.age_rating,

    d.director,
    d.duration,
    d.release_year

FROM product p
LEFT JOIN book b
    ON b.id = p.id
LEFT JOIN video_game vg
    ON vg.id = p.id
LEFT JOIN dvd d
    ON d.id = p.id
ORDER BY p.id;
*/

/*
SELECT COUNT(*) AS nombre_produits
FROM product;
*/

SELECT
    product_type,
    COUNT(*) AS nombre
FROM product
GROUP BY product_type;
