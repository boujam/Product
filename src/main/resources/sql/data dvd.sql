USE testpro3;

/* ============================================================
   CREATION DE 10 DVD
   ============================================================ */

DECLARE @ProductId BIGINT;


/* ============================================================
   DVD 1
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Inception',
    14.99,
    'Un thriller de science-fiction realise par Christopher Nolan.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'Christopher Nolan',
    148,
    2010
);


/* ============================================================
   DVD 2
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Jurassic Park',
    12.99,
    'Un film d aventure mettant en scene des dinosaures.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'Steven Spielberg',
    127,
    1993
);


/* ============================================================
   DVD 3
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Avatar',
    16.99,
    'Une aventure de science-fiction sur la planete Pandora.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'James Cameron',
    162,
    2009
);


/* ============================================================
   DVD 4
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Le Seigneur des Anneaux La Communaute de l Anneau',
    19.99,
    'Le premier volet de la trilogie de Peter Jackson.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'Peter Jackson',
    178,
    2001
);


/* ============================================================
   DVD 5
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Pulp Fiction',
    13.99,
    'Un film policier culte realise par Quentin Tarantino.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'Quentin Tarantino',
    154,
    1994
);


/* ============================================================
   DVD 6
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'The Dark Knight',
    15.99,
    'Un film de super-heros centre sur Batman et le Joker.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'Christopher Nolan',
    152,
    2008
);


/* ============================================================
   DVD 7
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Gladiator',
    11.99,
    'Une epopee historique dans la Rome antique.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'Ridley Scott',
    155,
    2000
);


/* ============================================================
   DVD 8
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Interstellar',
    17.99,
    'Une aventure spatiale autour de la survie de l humanite.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'Christopher Nolan',
    169,
    2014
);


/* ============================================================
   DVD 9
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Retour vers le Futur',
    10.99,
    'Une aventure de science-fiction autour du voyage dans le temps.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'Robert Zemeckis',
    116,
    1985
);


/* ============================================================
   DVD 10
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Le Roi Lion',
    12.99,
    'Un classique de l animation racontant le destin de Simba.',
    'dvd'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO dvd
(
    id,
    director,
    duration,
    release_year
)
VALUES
(
    @ProductId,
    'Roger Allers',
    88,
    1994
);


/* ============================================================
   VERIFICATION
   ============================================================ */

SELECT
    p.id,
    p.name,
    p.price,
    p.description,
    p.product_type,
    d.director,
    d.duration,
    d.release_year
FROM product p
INNER JOIN dvd d
    ON d.id = p.id
ORDER BY p.id;
