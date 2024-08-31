CREATE SCHEMA IF NOT EXISTS infoHub_backend;

CREATE TABLE IF NOT EXISTS infoHub_backend.article
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title         VARCHAR(30) NOT NULL,
    creation_time TIMESTAMP,
    updated_time  TIMESTAMP,
    views         BIGINT
);

CREATE TABLE IF NOT EXISTS infoHub_backend.user
(
    id           BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    creation_time TIMESTAMP,
    name         VARCHAR(30) NOT NULL,
    avatar       VARCHAR,
    email        VARCHAR(40)

);

CREATE TABLE IF NOT EXISTS infoHub_backend.rating
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    value      BIGINT,
    user_id    BIGINT references infoHub_backend.user (id) on delete cascade    not null,
    article_id BIGINT references infoHub_backend.article (id) on delete cascade not null
)