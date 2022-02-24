DROP TABLE IF EXISTS book_to_book_store, book_store, book, author, language;

CREATE TABLE language (
  id              INT     NOT NULL PRIMARY KEY,
  cd              CHAR(2)       NOT NULL,
  description     VARCHAR2(50)
);

CREATE TABLE author (
    id              INT     NOT NULL PRIMARY KEY,
    first_name      VARCHAR2(50),
    last_name       VARCHAR2(50)  NOT NULL,
    date_of_birth   DATE,
    year_of_birth   INT,
    distinguished   NUMBER(1)
);

CREATE TABLE book (
    id              INT     NOT NULL PRIMARY KEY,
    author_id       INT     NOT NULL,
    title           VARCHAR2(400) NOT NULL,
    published_in    INT     NOT NULL,
    language_id     INT     NOT NULL,

    CONSTRAINT fk_book_author     FOREIGN KEY (author_id)   REFERENCES author(id),
    CONSTRAINT fk_book_language   FOREIGN KEY (language_id) REFERENCES language(id)
);

CREATE TABLE book_store (
    id              IDENTITY NOT NULL PRIMARY KEY,
    name            VARCHAR2(400) NOT NULL UNIQUE
);

CREATE TABLE book_to_book_store (
    name            VARCHAR2(400) NOT NULL,
    book_id         INT       NOT NULL,
    stock           INT,

    PRIMARY KEY(name, book_id),
    CONSTRAINT fk_b2bs_book_store FOREIGN KEY (name)        REFERENCES book_store (name) ON DELETE CASCADE,
    CONSTRAINT fk_b2bs_book       FOREIGN KEY (book_id)     REFERENCES book (id)         ON DELETE CASCADE
);



-- CREATE TABLE author
-- (
--     id         INT         NOT NULL PRIMARY KEY,
--     first_name VARCHAR(50),
--     last_name  VARCHAR(50) NOT NULL
-- );
--
-- CREATE TABLE book
-- (
--     id    INT          NOT NULL PRIMARY KEY,
--     title VARCHAR(100) NOT NULL
-- );
--
-- CREATE TABLE author_book
-- (
--     author_id INT NOT NULL,
--     book_id   INT NOT NULL,
--
--     PRIMARY KEY (author_id, book_id),
--     CONSTRAINT fk_ab_author FOREIGN KEY (author_id) REFERENCES author (id)
--         ON UPDATE CASCADE ON DELETE CASCADE,
--     CONSTRAINT fk_ab_book FOREIGN KEY (book_id) REFERENCES book (id)
-- );
