CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    login    TEXT    NOT NULL UNIQUE,
    password TEXT    NOT NULL,
    secret   TEXT    NOT NULL,
    removed  BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL REFERENCES users UNIQUE,
    role_id BIGINT NOT NULL REFERENCES roles UNIQUE
);

CREATE TABLE tokens
(
    userId BIGINT NOT NULL REFERENCES users UNIQUE,
    token  TEXT PRIMARY KEY
);

CREATE TABLE feedback
(
    id          BIGSERIAL PRIMARY KEY,
    author_id   BIGINT    NOT NULL REFERENCES users,
    product     TEXT      NOT NULL,
    content     TEXT,
    date_time   TIMESTAMP NOT NULL
);
