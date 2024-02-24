DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE user_ticket (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(10) UNIQUE NOT NULL,
    ticket_id VARCHAR(6) UNIQUE NOT NULL
);


CREATE TABLE lottery (
    ticket_id VARCHAR(6) UNIQUE PRIMARY KEY,
    price INTEGER NOT NULL,
    amount INTEGER NOT NULL
);