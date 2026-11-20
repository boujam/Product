/* ==========================================
   INSERTION DE 10 LIVRES
   ========================================== */

DECLARE @Books TABLE (
    id BIGINT,
    isbn NVARCHAR(13),
    author NVARCHAR(255),
    publisher NVARCHAR(255),
    number_of_pages INT
);

DECLARE @ProductId BIGINT;


/* LIVRE 1 */
INSERT INTO product (name, price, description, product_type)
VALUES ('Clean Code', 39.90, 'Guide des bonnes pratiques de programmation.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9780132350884', 'Robert C. Martin', 'Prentice Hall', 464);


/* LIVRE 2 */
INSERT INTO product (name, price, description, product_type)
VALUES ('Effective Java', 45.00, 'Conseils pour maîtriser le langage Java.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9780134685991', 'Joshua Bloch', 'Addison-Wesley', 416);


/* LIVRE 3 */
INSERT INTO product (name, price, description, product_type)
VALUES ('Java: The Complete Reference', 49.90, 'Référence complète du langage Java.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9781260440232', 'Herbert Schildt', 'McGraw-Hill', 1248);


/* LIVRE 4 */
INSERT INTO product (name, price, description, product_type)
VALUES ('Head First Design Patterns', 42.50, 'Introduction aux modèles de conception.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9780596007126', 'Eric Freeman', 'O Reilly Media', 694);


/* LIVRE 5 */
INSERT INTO product (name, price, description, product_type)
VALUES ('The Pragmatic Programmer', 37.90, 'Conseils pour devenir un meilleur développeur.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9780135957059', 'David Thomas', 'Addison-Wesley', 352);


/* LIVRE 6 */
INSERT INTO product (name, price, description, product_type)
VALUES ('Design Patterns', 44.90, 'Les principaux modèles de conception logicielle.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9780201633610', 'Erich Gamma', 'Addison-Wesley', 395);


/* LIVRE 7 */
INSERT INTO product (name, price, description, product_type)
VALUES ('Spring in Action', 46.90, 'Apprendre le framework Spring.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9781617297571', 'Craig Walls', 'Manning', 520);


/* LIVRE 8 */
INSERT INTO product (name, price, description, product_type)
VALUES ('Learning SQL', 32.90, 'Introduction aux bases de données SQL.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9781492057611', 'Alan Beaulieu', 'O Reilly Media', 384);


/* LIVRE 9 */
INSERT INTO product (name, price, description, product_type)
VALUES ('Refactoring', 43.90, 'Améliorer la structure du code existant.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9780134757599', 'Martin Fowler', 'Addison-Wesley', 448);


/* LIVRE 10 */
INSERT INTO product (name, price, description, product_type)
VALUES ('Introduction to Algorithms', 65.00, 'Fondamentaux des algorithmes informatiques.', 'book');

SET @ProductId = SCOPE_IDENTITY();

INSERT INTO book (id, isbn, author, publisher, number_of_pages)
VALUES (@ProductId, '9780262046305', 'Thomas H. Cormen', 'MIT Press', 1312);


/* ==========================================
   VERIFICATION DES 10 LIVRES
   ========================================== */

SELECT
    p.id,
    p.name,
    p.price,
    p.description,
    b.isbn,
    b.author,
    b.publisher,
    b.number_of_pages
FROM product p
INNER JOIN book b ON p.id = b.id
ORDER BY p.id;
