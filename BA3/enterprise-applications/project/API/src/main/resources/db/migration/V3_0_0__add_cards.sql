CREATE TABLE "tag" (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    normalized_name VARCHAR NOT NULL,
    hex_colour VARCHAR
);

CREATE INDEX
    IF NOT EXISTS
    "index_tag_normalzed_name"
    ON "tag"(normalized_name);

CREATE TABLE "deck" (
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    description VARCHAR,
    user_id BIGINT REFERENCES "user"(id)
);

CREATE TABLE "card" (
  id SERIAL PRIMARY KEY,
  type INT NOT NULL,
  difficulty INT NOT NULL,
  question VARCHAR NOT NULL,
  hint VARCHAR,
  information VARCHAR,
  deck_id BIGINT REFERENCES "deck"(id)
);

CREATE TABLE "pivot_deck_tags" (
    deck_id BIGINT REFERENCES "deck"(id),
    tag_id BIGINT REFERENCES "tag"(id)
)