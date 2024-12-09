

CREATE TABLE "session" (
    id SERIAL PRIMARY KEY,
    start TIMESTAMP NOT NULL DEFAULT NOW(),
    finish TIMESTAMP,
    deck_id BIGINT REFERENCES deck(id),
    user_id BIGINT REFERENCES "user"(id)
);

CREATE TABLE "sessionAnswers" (
    id SERIAL PRIMARY KEY,
    session_id BIGINT REFERENCES session(id),
    card_id BIGINT REFERENCES card(id),
    answer_id BIGINT REFERENCES answer(id),
    user_answer VARCHAR NOT NULL
);