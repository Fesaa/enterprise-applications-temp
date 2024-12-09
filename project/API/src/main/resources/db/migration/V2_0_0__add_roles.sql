CREATE TABLE "role" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE "pivot_user_role" (
   user_id BIGINT NOT NULL,
   role_id BIGINT NOT NULL,
   PRIMARY KEY (user_id, role_id),
   FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
   FOREIGN KEY (role_id) REFERENCES "role" (id) ON DELETE CASCADE
);

INSERT INTO role (name) VALUES (
                         ('ADMIN')
                        );
