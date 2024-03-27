CREATE TABLE books
(
    id       UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title    varchar(250),
    author   varchar(250),
    isbn     varchar(15),
    quantity int
);

INSERT INTO books (id, title, author, isbn, quantity)
VALUES ('6fb541c6-710a-4929-a8a0-bd1168646403', 'The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 10),
       ('ad8dc206-82c1-4a3f-96fb-240aff6f5cf0', 'To Kill a Mockingbird', 'Harper Lee', '9780061120084', 8),
       ('3bd14ba1-628b-4afa-a65c-b30e43f053a1', '1984', 'George Orwell', '9780451524935', 12),
       ('c460c258-d1a1-431f-90c3-9bf2d5c0b1d8', 'Pride and Prejudice', 'Jane Austen', '9780141439518', 6),
       ('08847cb0-5816-476a-a96f-6af6a4e7e745', 'The Catcher in the Rye', 'J.D. Salinger', '9780316769488', 9);
