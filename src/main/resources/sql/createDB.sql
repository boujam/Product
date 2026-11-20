CREATE DATABASE testpro3;

/* =====================================================
   1. TABLE PRODUCT (CLASSE MERE)
   ===================================================== */

CREATE TABLE product (
    id BIGINT IDENTITY(1,1) NOT NULL,

    name NVARCHAR(100) NOT NULL,

    price DECIMAL(10,2) NOT NULL,

    description NVARCHAR(1000) NULL,

    product_type NVARCHAR(31) NOT NULL,

    CONSTRAINT PK_product PRIMARY KEY (id),

    CONSTRAINT CK_product_price
        CHECK (price >= 0),

    CONSTRAINT CK_product_type
        CHECK (product_type IN ('book', 'dvd', 'video-game'))
);


/* =====================================================
   2. TABLE BOOK (CLASSE FILLE)
   ===================================================== */

CREATE TABLE book (
    id BIGINT NOT NULL,

    isbn NVARCHAR(13) NOT NULL,

    author NVARCHAR(255) NOT NULL,

    publisher NVARCHAR(255) NOT NULL,

    number_of_pages INT NOT NULL,

    CONSTRAINT PK_book PRIMARY KEY (id),

    CONSTRAINT FK_book_product
        FOREIGN KEY (id)
        REFERENCES product(id)
        ON DELETE CASCADE,

    CONSTRAINT UQ_book_isbn UNIQUE (isbn),

    CONSTRAINT CK_book_isbn
        CHECK (
            LEN(isbn) BETWEEN 10 AND 13
            AND isbn NOT LIKE '%[^0-9]%'
        ),

    CONSTRAINT CK_book_pages
        CHECK (number_of_pages >= 1)
);


/* =====================================================
   3. TABLE DVD (CLASSE FILLE)
   ===================================================== */

CREATE TABLE dvd (
    id BIGINT NOT NULL,

    director NVARCHAR(255) NOT NULL,

    duration INT NOT NULL,

    release_year INT NOT NULL,

    CONSTRAINT PK_dvd PRIMARY KEY (id),

    CONSTRAINT FK_dvd_product
        FOREIGN KEY (id)
        REFERENCES product(id)
        ON DELETE CASCADE,

    CONSTRAINT CK_dvd_duration
        CHECK (duration BETWEEN 1 AND 600),

    CONSTRAINT CK_dvd_release_year
        CHECK (release_year >= 1888)
);


/* =====================================================
   4. TABLE VIDEO_GAME (CLASSE FILLE)
   ===================================================== */

CREATE TABLE video_game (
    id BIGINT NOT NULL,

    developer NVARCHAR(255) NOT NULL,

    platform NVARCHAR(255) NOT NULL,

    genre NVARCHAR(255) NOT NULL,

    age_rating NVARCHAR(7) NOT NULL,

    CONSTRAINT PK_video_game PRIMARY KEY (id),

    CONSTRAINT FK_video_game_product
        FOREIGN KEY (id)
        REFERENCES product(id)
        ON DELETE CASCADE,

    CONSTRAINT CK_video_game_age_rating
        CHECK (
            age_rating IN (
                'PEGI 3',
                'PEGI 7',
                'PEGI 12',
                'PEGI 16',
                'PEGI 18'
            )
        )
);


/* =====================================================
   FIN DU SCRIPT
   ===================================================== */