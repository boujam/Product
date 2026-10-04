USE testpro3;

/* ============================================================
   CREATION DE 10 JEUX VIDEO
   ============================================================ */

DECLARE @ProductId BIGINT;


/* ============================================================
   JEU VIDEO 1
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'The Legend of Zelda Tears of the Kingdom',
    69.99,
    'Une aventure epique dans le royaume d Hyrule.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'Nintendo',
    'Nintendo Switch',
    'Adventure',
    'PEGI 12'
);


/* ============================================================
   JEU VIDEO 2
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Elden Ring',
    59.99,
    'Un RPG d action dans un vaste monde fantastique.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'FromSoftware',
    'PlayStation 5',
    'RPG',
    'PEGI 16'
);


/* ============================================================
   JEU VIDEO 3
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'The Witcher 3 Wild Hunt',
    39.99,
    'Une aventure RPG dans un monde fantastique immense.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'CD Projekt Red',
    'PC',
    'RPG',
    'PEGI 18'
);


/* ============================================================
   JEU VIDEO 4
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Grand Theft Auto V',
    29.99,
    'Un jeu d action et d exploration dans une grande ville ouverte.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'Rockstar Games',
    'PlayStation 5',
    'Open World',
    'PEGI 18'
);


/* ============================================================
   JEU VIDEO 5
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Super Mario Odyssey',
    49.99,
    'Une aventure de plateforme mettant en scene Mario.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'Nintendo',
    'Nintendo Switch',
    'Platform',
    'PEGI 7'
);


/* ============================================================
   JEU VIDEO 6
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Resident Evil Village',
    44.99,
    'Une aventure horrifique dans un village mysterieux.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'Capcom',
    'PlayStation 5',
    'Horror',
    'PEGI 18'
);


/* ============================================================
   JEU VIDEO 7
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Forza Horizon 5',
    54.99,
    'Un jeu de course en monde ouvert.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'Electronic Arts',
    'Xbox Series X',
    'Racing',
    'PEGI 3'
);


/* ============================================================
   JEU VIDEO 8
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Minecraft',
    26.99,
    'Un jeu de construction et de survie dans un monde ouvert.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'Mojang',
    'PC',
    'Simulation',
    'PEGI 7'
);


/* ============================================================
   JEU VIDEO 9
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'God of War Ragnarok',
    69.99,
    'Une aventure mythologique avec Kratos et Atreus.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'Santa Monica Studio',
    'PlayStation 5',
    'Action',
    'PEGI 18'
);


/* ============================================================
   JEU VIDEO 10
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Tekken 8',
    64.99,
    'Un jeu de combat avec de nombreux personnages.',
    'video-game'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game
(
    id,
    developer,
    platform,
    genre,
    age_rating
)
VALUES
(
    @ProductId,
    'Bandai Namco',
    'PlayStation 5',
    'Fighting',
    'PEGI 12'
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
    vg.developer,
    vg.platform,
    vg.genre,
    vg.age_rating
FROM product p
INNER JOIN video_game vg
    ON vg.id = p.id
ORDER BY p.id;