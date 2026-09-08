USE testpro3;

/*
SELECT
    COLUMN_NAME,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'product'
ORDER BY ORDINAL_POSITION;
*/


ALTER TABLE product
ALTER COLUMN description NVARCHAR(1000);



SET NOCOUNT ON;

DECLARE @NombreDeLivres INT = 1000000;

-- ============================================================
-- 0. VIDER LA TABLE ET RÉINITIALISER L'IDENTITY
-- ============================================================

TRUNCATE TABLE product;


-- ============================================================
-- 1. GÉNÉRATION DE 1 000 000 NUMÉROS
--
-- 10 × 10 × 10 × 10 × 10 × 10 = 1 000 000
-- ============================================================

;WITH
N1 AS
(
    SELECT n
    FROM (VALUES
        (0),(1),(2),(3),(4),
        (5),(6),(7),(8),(9)
    ) AS T(n)
),

Nombres AS
(
    SELECT
        ROW_NUMBER() OVER
        (
            ORDER BY
                A.n,
                B.n,
                C.n,
                D.n,
                E.n,
                F.n
        ) AS numero

    FROM N1 A
    CROSS JOIN N1 B
    CROSS JOIN N1 C
    CROSS JOIN N1 D
    CROSS JOIN N1 E
    CROSS JOIN N1 F
),


-- ============================================================
-- 2. PRÉFIXES
-- ============================================================

Prefixes AS
(
    SELECT *
    FROM
    (
        VALUES
        (1,  'La'),
        (2,  'Le'),
        (3,  'Les'),
        (4,  'L'''),
        (5,  'Un'),
        (6,  'Une'),
        (7,  'Des'),
        (8,  'Quand'),
        (9,  'Après'),
        (10, 'Avant'),
        (11, 'Sous'),
        (12, 'Au-delà de'),
        (13, 'Dans'),
        (14, 'Par-delà'),
        (15, 'À travers'),
        (16, 'Le dernier'),
        (17, 'La dernière'),
        (18, 'Les derniers'),
        (19, 'Les dernières'),
        (20, 'Ceux qui')
    ) AS P(id, valeur)
),


-- ============================================================
-- 3. THÈMES
-- ============================================================

Themes AS
(
    SELECT *
    FROM
    (
        VALUES
        (1,  'Aube'),
        (2,  'Nuit'),
        (3,  'Silence'),
        (4,  'Lumière'),
        (5,  'Ombres'),
        (6,  'Cendres'),
        (7,  'Étoiles'),
        (8,  'Souvenirs'),
        (9,  'Rêves'),
        (10, 'Mensonges'),
        (11, 'Promesses'),
        (12, 'Secrets'),
        (13, 'Frontières'),
        (14, 'Horizons'),
        (15, 'Royaumes'),
        (16, 'Jardins'),
        (17, 'Rivières'),
        (18, 'Montagnes'),
        (19, 'Océans'),
        (20, 'Vents'),
        (21, 'Brumes'),
        (22, 'Flammes'),
        (23, 'Échos'),
        (24, 'Fantômes'),
        (25, 'Voyageurs'),
        (26, 'Héritiers'),
        (27, 'Exilés'),
        (28, 'Oubliés'),
        (29, 'Insoumis'),
        (30, 'Derniers jours'),
        (31, 'Premiers matins'),
        (32, 'Longues nuits'),
        (33, 'Heures perdues'),
        (34, 'Rêves brisés'),
        (35, 'Mondes disparus'),
        (36, 'Villes invisibles'),
        (37, 'Portes closes'),
        (38, 'Chemins oubliés'),
        (39, 'Terres lointaines'),
        (40, 'Saisons perdues')
    ) AS T(id, valeur)
),


-- ============================================================
-- 4. LIEUX
-- ============================================================

Lieux AS
(
    SELECT *
    FROM
    (
        VALUES
        (1,  'Valoria'),
        (2,  'Montreuil'),
        (3,  'Clairval'),
        (4,  'Rochebrune'),
        (5,  'Bellecombe'),
        (6,  'Saint-Aubin'),
        (7,  'Port-Royal'),
        (8,  'Grandval'),
        (9,  'Boisclair'),
        (10, 'Lunéria'),
        (11, 'Néboria'),
        (12, 'Eldoria'),
        (13, 'Astéria'),
        (14, 'Soléria'),
        (15, 'Valombre'),
        (16, 'Méridia'),
        (17, 'Orphéa'),
        (18, 'Célestia'),
        (19, 'Arcania'),
        (20, 'Sylvaris'),
        (21, 'Eryndor'),
        (22, 'Drakonia'),
        (23, 'Aurélia'),
        (24, 'Cassandre'),
        (25, 'Élysée'),
        (26, 'Florence'),
        (27, 'Aveline'),
        (28, 'Lysandre'),
        (29, 'Astrelle'),
        (30, 'Valdoria'),
        (31, 'Nova'),
        (32, 'Belmont'),
        (33, 'Théodoria'),
        (34, 'Eldham'),
        (35, 'Ravenwood'),
        (36, 'Atlantis'),
        (37, 'Némésis'),
        (38, 'Montelune'),
        (39, 'Lacombe'),
        (40, 'Saint-Martin')
    ) AS L(id, valeur)
),


-- ============================================================
-- 5. AUTEURS
-- ============================================================

Auteurs AS
(
    SELECT *
    FROM
    (
        VALUES
        (1,  'Élise Moreau'),
        (2,  'Julien Delorme'),
        (3,  'Camille Laurent'),
        (4,  'Thomas Valmont'),
        (5,  'Clara Beaumont'),
        (6,  'Nicolas Delaunay'),
        (7,  'Sophie Renard'),
        (8,  'Antoine Mercier'),
        (9,  'Marion Lefèvre'),
        (10, 'Gabriel Fontaine'),
        (11, 'Alice Montfort'),
        (12, 'Louis Caron'),
        (13, 'Émilie Rousseau'),
        (14, 'Victor Lambert'),
        (15, 'Claire Morel'),
        (16, 'Hugo Delattre'),
        (17, 'Léa Fournier'),
        (18, 'Mathieu Girard'),
        (19, 'Anaïs Chevalier'),
        (20, 'Arthur Masson'),
        (21, 'Jeanne Dupont'),
        (22, 'Simon Gauthier'),
        (23, 'Louise Perrin'),
        (24, 'Maxime Renard'),
        (25, 'Chloé Martin'),
        (26, 'Paul Béranger'),
        (27, 'Eva Garnier'),
        (28, 'Alexandre Roche'),
        (29, 'Manon Durand'),
        (30, 'Nathan Olivier'),
        (31, 'Sarah Fontaine'),
        (32, 'Lucas Moreau'),
        (33, 'Juliette Bernard'),
        (34, 'Thomas Aubert'),
        (35, 'Emma Charpentier'),
        (36, 'Arthur Lefort'),
        (37, 'Nina Berger'),
        (38, 'Baptiste Renard'),
        (39, 'Margaux Petit'),
        (40, 'Victor Delmas'),
        (41, 'Éva Morel'),
        (42, 'Gabriel Laurent'),
        (43, 'Louise Mercier'),
        (44, 'Adrien Beaumont'),
        (45, 'Clémence Roche'),
        (46, 'Martin Delorme'),
        (47, 'Iris Fontaine'),
        (48, 'Théo Lambert'),
        (49, 'Jeanne Valmont'),
        (50, 'Oscar Renard')
    ) AS A(id, nom)
),


-- ============================================================
-- 6. GENRES
-- ============================================================

Genres AS
(
    SELECT *
    FROM
    (
        VALUES
        (1,  'Thriller'),
        (2,  'Roman policier'),
        (3,  'Fantasy'),
        (4,  'Science-fiction'),
        (5,  'Romance'),
        (6,  'Aventure'),
        (7,  'Historique'),
        (8,  'Drame'),
        (9,  'Littérature'),
        (10, 'Mystère'),
        (11, 'Fantastique'),
        (12, 'Dystopie'),
        (13, 'Jeunesse'),
        (14, 'Psychologique'),
        (15, 'Épique'),
        (16, 'Contemporain'),
        (17, 'Philosophique'),
        (18, 'Uchronie'),
        (19, 'Voyage'),
        (20, 'Anticipation')
    ) AS G(id, nom)
)


-- ============================================================
-- 7. INSERTION DES 1 000 000 LIVRES
-- ============================================================

INSERT INTO product
(
    name,
    price,
    description,
    isbn
)

SELECT

    -- ========================================================
    -- NOM
    -- ========================================================

    CASE (N.numero % 12)

        WHEN 0 THEN
            CONCAT('La ', T.valeur, ' de ', L.valeur)

        WHEN 1 THEN
            CONCAT('Le Secret des ', T.valeur, ' de ', L.valeur)

        WHEN 2 THEN
            CONCAT('Les ', T.valeur, ' de ', L.valeur)

        WHEN 3 THEN
            CONCAT('La Mémoire des ', T.valeur, ' de ', L.valeur)

        WHEN 4 THEN
            CONCAT('Le Dernier ', T.valeur, ' de ', L.valeur)

        WHEN 5 THEN
            CONCAT('La Dernière ', T.valeur, ' de ', L.valeur)

        WHEN 6 THEN
            CONCAT('Les Héritiers des ', T.valeur, ' de ', L.valeur)

        WHEN 7 THEN
            CONCAT('Les Gardiens des ', T.valeur, ' de ', L.valeur)

        WHEN 8 THEN
            CONCAT('Au cœur des ', T.valeur, ' de ', L.valeur)

        WHEN 9 THEN
            CONCAT('Quand viennent les ', T.valeur, ' de ', L.valeur)

        WHEN 10 THEN
            CONCAT('La Maison des ', T.valeur, ' de ', L.valeur)

        ELSE
            CONCAT('Les Chroniques des ', T.valeur, ' de ', L.valeur)

    END

    + CASE (N.numero % 17)

        WHEN 0 THEN ' : Tome I'
        WHEN 1 THEN ' : Tome II'
        WHEN 2 THEN ' : Tome III'
        WHEN 3 THEN ' : Le commencement'
        WHEN 4 THEN ' : Le dernier chapitre'
        WHEN 5 THEN ' : La révélation'
        WHEN 6 THEN ' : L''ultime voyage'
        WHEN 7 THEN ' : Le nouveau monde'
        WHEN 8 THEN ' : La cité perdue'
        WHEN 9 THEN ' : Les héritiers'
        WHEN 10 THEN ' : La dernière frontière'
        WHEN 11 THEN ' : Le retour'
        WHEN 12 THEN ' : L''horizon'
        WHEN 13 THEN ' : Le pacte'
        WHEN 14 THEN ' : Le passage'
        WHEN 15 THEN ' : L''origine'
        ELSE ''

      END

    + ' - Volume '
    + CAST(((N.numero - 1) % 1000) + 1 AS VARCHAR(4))

    AS name,


    -- ========================================================
    -- PRIX
    -- Entre 7,90 € et 49,90 €
    -- ========================================================

    CAST(
        7.90
        + ((N.numero * 47) % 4201) / 100.0
        AS DECIMAL(10,2)
    ) AS price,


    -- ========================================================
    -- DESCRIPTION
    -- ========================================================

    CONCAT(

        'Roman de ',
        G.nom,
        ' écrit par ',
        A.nom,
        '. ',

        CASE G.nom

            WHEN 'Thriller' THEN
                'Une enquête dangereuse entraîne le lecteur au cœur d''une affaire où chaque indice peut bouleverser la vérité. '

            WHEN 'Roman policier' THEN
                'Une enquête minutieuse confronte un enquêteur déterminé à une affaire dont les ramifications dépassent toutes les attentes. '

            WHEN 'Fantasy' THEN
                'Dans un monde où la magie appartient encore aux anciens royaumes, une génération inattendue doit affronter une menace oubliée. '

            WHEN 'Science-fiction' THEN
                'Dans un futur où les progrès technologiques ont transformé la société, une découverte remet en question les certitudes de l''humanité. '

            WHEN 'Romance' THEN
                'Deux destins que tout semble opposer se rencontrent et découvrent que certaines décisions peuvent changer une vie entière. '

            WHEN 'Aventure' THEN
                'Une expédition hors du commun conduit les protagonistes vers des territoires inconnus et des découvertes inattendues. '

            WHEN 'Historique' THEN
                'À une époque marquée par de profonds bouleversements, plusieurs destins se croisent au milieu des événements de leur temps. '

            WHEN 'Drame' THEN
                'Une histoire profondément humaine autour de la famille, des choix difficiles et des secondes chances. '

            WHEN 'Littérature' THEN
                'Un récit consacré aux relations humaines, à la mémoire et aux petits événements qui peuvent transformer une existence. '

            WHEN 'Mystère' THEN
                'Un événement mystérieux pousse les personnages à rechercher une vérité soigneusement dissimulée depuis plusieurs années. '

            WHEN 'Fantastique' THEN
                'La frontière entre le monde réel et l''imaginaire commence à disparaître lorsqu''un événement inexplicable bouleverse le quotidien. '

            WHEN 'Dystopie' THEN
                'Dans une société qui prétend avoir supprimé toute forme de conflit, quelques individus découvrent progressivement une réalité bien différente. '

            WHEN 'Jeunesse' THEN
                'Une aventure accessible et pleine de découvertes dans laquelle l''amitié et le courage occupent une place centrale. '

            WHEN 'Psychologique' THEN
                'Une plongée dans les pensées d''un personnage confronté à ses souvenirs, ses choix et ses propres contradictions. '

            WHEN 'Épique' THEN
                'Une grande fresque où plusieurs générations doivent faire face aux conséquences d''une ancienne décision. '

            WHEN 'Contemporain' THEN
                'Un récit actuel consacré aux relations humaines, aux choix personnels et aux changements qui façonnent notre époque. '

            WHEN 'Philosophique' THEN
                'Une réflexion romanesque sur la liberté, le temps, la mémoire et la place de l''individu dans le monde. '

            WHEN 'Uchronie' THEN
                'Une autre version de l''Histoire dans laquelle un événement différent a profondément transformé le destin des sociétés. '

            WHEN 'Voyage' THEN
                'Un long voyage à travers plusieurs régions permet aux personnages de découvrir des cultures, des paysages et surtout eux-mêmes. '

            ELSE
                'Une vision possible du futur dans laquelle les choix technologiques et humains redessinent progressivement le monde. '

        END,

        ' L''histoire se déroule principalement à ',
        L.valeur,
        ' et met en scène des personnages confrontés à des décisions déterminantes. ',

        'Collection littéraire ',
        RIGHT(
            '0000'
            + CAST(((N.numero - 1) % 1000) + 1 AS VARCHAR(4)),
            4
        ),
        '. Référence interne : ',
        CAST(N.numero AS VARCHAR(10)),
        '.'

    ) AS description,


    -- ========================================================
    -- ISBN DE TEST
    -- ========================================================

    CONCAT(
        '979',
        RIGHT(
            '0000000000'
            + CAST(N.numero AS VARCHAR(10)),
            10
        )
    ) AS isbn


FROM Nombres N

INNER JOIN Prefixes P
    ON P.id = ((N.numero - 1) % 20) + 1

INNER JOIN Themes T
    ON T.id = ((N.numero * 7 - 1) % 40) + 1

INNER JOIN Lieux L
    ON L.id = ((N.numero * 13 - 1) % 40) + 1

INNER JOIN Auteurs A
    ON A.id = ((N.numero * 17 - 1) % 50) + 1

INNER JOIN Genres G
    ON G.id = ((N.numero * 19 - 1) % 20) + 1;


-- ============================================================
-- 8. VÉRIFICATIONS
-- ============================================================

SELECT
    COUNT(*) AS NombreDeLivres,
    MIN(id) AS PremierId,
    MAX(id) AS DernierId
FROM product;


-- ============================================================
-- 9. VÉRIFICATION DES ISBN
-- ============================================================

SELECT
    COUNT(*) AS NombreISBN,
    COUNT(DISTINCT isbn) AS ISBNUniques
FROM product;


-- ============================================================
-- 10. APERÇU DES PREMIERS LIVRES
-- ============================================================

SELECT TOP 20
    id,
    name,
    price,
    description,
    isbn
FROM product
ORDER BY id;
