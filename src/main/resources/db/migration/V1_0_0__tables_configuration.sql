CREATE TABLE books
(
    id       UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title    varchar(250),
    author   varchar(250),
    isbn     varchar(15),
    quantity int
);

INSERT INTO books (title, author, isbn, quantity)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 10),
       ('To Kill a Mockingbird', 'Harper Lee', '9780061120084', 8),
       ('1984', 'George Orwell', '9780451524935', 12),
       ('Pride and Prejudice', 'Jane Austen', '9780141439518', 6),
       ('The Catcher in the Rye', 'J.D. Salinger', '9780316769488', 9);
