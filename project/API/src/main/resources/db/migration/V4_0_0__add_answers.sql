CREATE TABLE "answer" (
    id SERIAL PRIMARY KEY,
    card_id BIGINT REFERENCES "card"(id),
    answer VARCHAR NOT NULL,
    correct BOOLEAN NOT NULL DEFAULT FALSE
 );