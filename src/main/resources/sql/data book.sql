USE testpro3;

/* ============================================================
   CREATION DE 10 LIVRES
   ============================================================ */

DECLARE @ProductId BIGINT;


/* ============================================================
   LIVRE 1
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Le Seigneur des Anneaux',
    29.90,
    'Un grand classique de la litterature fantastique.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9780261102385',
    'J.R.R. Tolkien',
    'HarperCollins',
    1178
);


/* ============================================================
   LIVRE 2
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    '1984',
    12.50,
    'Un roman dystopique sur une societe sous surveillance.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9780451524935',
    'George Orwell',
    'Penguin Books',
    328
);


/* ============================================================
   LIVRE 3
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Vingt mille lieues sous les mers',
    15.90,
    'Une aventure extraordinaire dans les profondeurs des oceans.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9782070513001',
    'Jules Verne',
    'Gallimard',
    512
);


/* ============================================================
   LIVRE 4
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Le Comte de Monte-Cristo',
    19.90,
    'Une histoire de vengeance, de justice et de redemption.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9782253001201',
    'Alexandre Dumas',
    'Hachette',
    1248
);


/* ============================================================
   LIVRE 5
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Les Miserables',
    18.50,
    'Le destin de Jean Valjean dans la France du XIXe siecle.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9782070409220',
    'Victor Hugo',
    'Gallimard',
    1463
);


/* ============================================================
   LIVRE 6
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Fondation',
    14.90,
    'Une oeuvre majeure de science-fiction.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9780553293357',
    'Isaac Asimov',
    'Random House',
    296
);


/* ============================================================
   LIVRE 7
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Dune',
    17.90,
    'Une epopee de science-fiction sur la planete Arrakis.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9780441172719',
    'Frank Herbert',
    'Ace Books',
    688
);


/* ============================================================
   LIVRE 8
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Harry Potter a l ecole des sorciers',
    11.90,
    'Les premieres aventures de Harry Potter a Poudlard.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9782070541270',
    'J.K. Rowling',
    'Gallimard',
    320
);


/* ============================================================
   LIVRE 9
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Le Nom de la Rose',
    16.90,
    'Une enquete medievale dans une abbaye italienne.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9782253002857',
    'Umberto Eco',
    'Le Livre de Poche',
    672
);


/* ============================================================
   LIVRE 10
   ============================================================ */

INSERT INTO product (name, price, description, product_type)
VALUES (
    'Le Petit Prince',
    9.90,
    'Un conte poetique et philosophique intemporel.',
    'book'
);

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (
    @ProductId,
    '9782070612758',
    'Antoine de Saint-Exupery',
    'Gallimard',
    96
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
    b.isbn,
    b.author,
    b.publisher,
    b.number_of_pages
FROM product p
INNER JOIN book b
    ON b.id = p.id
ORDER BY p.id;
