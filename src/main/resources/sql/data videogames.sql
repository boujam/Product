/* =====================================================
   CREATION DE 10 JEUX VIDEO
   Tables : product et video_game
   Strategie JPA : InheritanceType.JOINED
   ===================================================== */

SET NOCOUNT ON;

DECLARE @ProductId BIGINT;


/* =====================================================
   VIDEO GAME 1
   ===================================================== */

INSERT INTO product (name, price, description, product_type)
VALUES ('The Witcher 3 Wild Hunt', 39.90,
        'Jeu de role en monde ouvert dans un univers fantastique.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'CD Projekt Red', 'PC, PS5, Xbox Series X',
        'Action RPG', 'PEGI 18');


/* VIDEO GAME 2 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Minecraft', 29.90,
        'Jeu de construction et de survie dans un monde en blocs.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'Mojang Studios', 'PC, PS5, Xbox, Switch',
        'Sandbox', 'PEGI 7');


/* VIDEO GAME 3 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Grand Theft Auto V', 24.90,
        'Jeu d action en monde ouvert dans la ville de Los Santos.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'Rockstar Games', 'PC, PS5, Xbox',
        'Action Adventure', 'PEGI 18');


/* VIDEO GAME 4 */

INSERT INTO product (name, price, description, product_type)
VALUES ('The Legend of Zelda Breath of the Wild', 49.90,
        'Aventure et exploration dans le royaume de Hyrule.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'Nintendo', 'Nintendo Switch',
        'Action Adventure', 'PEGI 12');


/* VIDEO GAME 5 */

INSERT INTO product (name, price, description, product_type)
VALUES ('FIFA 25', 59.90,
        'Simulation de football avec des equipes internationales.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'EA Sports', 'PC, PS5, Xbox',
        'Sports', 'PEGI 3');


/* VIDEO GAME 6 */

INSERT INTO product (name, price, description, product_type)
VALUES ('God of War Ragnarok', 54.90,
        'Aventure mythologique avec Kratos et Atreus.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'Santa Monica Studio', 'PS4, PS5',
        'Action Adventure', 'PEGI 18');


/* VIDEO GAME 7 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Cyberpunk 2077', 34.90,
        'Jeu de role futuriste dans Night City.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'CD Projekt Red', 'PC, PS5, Xbox',
        'Action RPG', 'PEGI 18');


/* VIDEO GAME 8 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Animal Crossing New Horizons', 44.90,
        'Simulation de vie sur une ile tropicale.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'Nintendo', 'Nintendo Switch',
        'Simulation', 'PEGI 3');


/* VIDEO GAME 9 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Elden Ring', 49.90,
        'Jeu de role action dans un vaste monde fantastique.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'FromSoftware', 'PC, PS5, Xbox',
        'Action RPG', 'PEGI 16');


/* VIDEO GAME 10 */

INSERT INTO product (name, price, description, product_type)
VALUES ('Gran Turismo 7', 39.90,
        'Simulation automobile avec de nombreux vehicules.',
        'video-game');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO video_game (id, developer, platform, genre, age_rating)
VALUES (@ProductId, 'Polyphony Digital', 'PS4, PS5',
        'Racing', 'PEGI 3');


/* =====================================================
   VERIFICATION DES 10 JEUX VIDEO
   ===================================================== */

SELECT
    p.id,
    p.name,
    p.price,
    p.description,
    vg.developer,
    vg.platform,
    vg.genre,
    vg.age_rating
FROM product p
INNER JOIN video_game vg ON p.id = vg.id
WHERE p.product_type = 'video-game'
ORDER BY p.id;


/* =====================================================
   STATISTIQUES
   ===================================================== */

SELECT
    COUNT(*) AS total_video_games,
    MIN(p.price) AS minimum_price,
    MAX(p.price) AS maximum_price,
    AVG(p.price) AS average_price
FROM product p
INNER JOIN video_game vg ON p.id = vg.id;