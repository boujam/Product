/* =====================================================
   CREATION DE 10 DVD
   Tables : product et dvd
   Strategie JPA : InheritanceType.JOINED
   ===================================================== */

SET NOCOUNT ON;

DECLARE @ProductId BIGINT;


/* =====================================================
   DVD 1
   ===================================================== */

INSERT INTO product (name, price, description, product_type)
VALUES ('Inception', 14.90, 'Film de science-fiction realise par Christopher Nolan.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'Christopher Nolan', 148, 2010);


/* DVD 2 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Interstellar', 16.90, 'Voyage spatial et exploration de l espace.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'Christopher Nolan', 169, 2014);


/* DVD 3 */

INSERT INTO product (name, price, description, product_type)
VALUES ('The Dark Knight', 15.90, 'Film de super-heros mettant en scene Batman.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'Christopher Nolan', 152, 2008);


/* DVD 4 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Titanic', 12.90, 'Histoire romantique sur le celebre paquebot Titanic.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'James Cameron', 194, 1997);


/* DVD 5 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Avatar', 18.90, 'Aventure de science-fiction sur la planete Pandora.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'James Cameron', 162, 2009);


/* DVD 6 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Gladiator', 13.90, 'Epopee historique dans la Rome antique.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'Ridley Scott', 155, 2000);


/* DVD 7 */

INSERT INTO product (name, price, description, product_type)
VALUES ('The Matrix', 14.50, 'Film de science-fiction sur la realite virtuelle.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'Lana et Lilly Wachowski', 136, 1999);


/* DVD 8 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Jurassic Park', 11.90, 'Des dinosaures sont ramenes a la vie dans un parc.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'Steven Spielberg', 127, 1993);


/* DVD 9 */

INSERT INTO product (name, price, description, product_type)
VALUES ('The Lord of the Rings', 19.90, 'Aventure fantastique en Terre du Milieu.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'Peter Jackson', 178, 2001);


/* DVD 10 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Forrest Gump', 12.50, 'Histoire touchante de la vie de Forrest Gump.', 'dvd');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd (id, director, duration, release_year)
VALUES (@ProductId, 'Robert Zemeckis', 142, 1994);


/* =====================================================
   VERIFICATION DES 10 DVD
   ===================================================== */

SELECT
    p.id,
    p.name,
    p.price,
    p.description,
    d.director,
    d.duration,
    d.release_year
FROM product p
INNER JOIN dvd d ON p.id = d.id
WHERE p.product_type = 'dvd'
ORDER BY p.id;


/* =====================================================
   STATISTIQUES
   ===================================================== */

SELECT
    COUNT(*) AS total_dvd,
    MIN(p.price) AS minimum_price,
    MAX(p.price) AS maximum_price,
    AVG(p.price) AS average_price,
    AVG(d.duration) AS average_duration
FROM product p
INNER JOIN dvd d ON p.id = d.id;