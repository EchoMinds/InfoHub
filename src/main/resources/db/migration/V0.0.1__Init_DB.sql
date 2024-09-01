CREATE TABLE IF NOT EXISTS article
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title         VARCHAR NOT NULL,
    views         BIGINT,

    created_at   timestamp with time zone not null,
    updated_at   timestamp with time zone not null
);

CREATE TABLE IF NOT EXISTS "user"
(
    id           BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "name"       VARCHAR NOT NULL,
    avatar_url   VARCHAR,
    email        VARCHAR not null,
    password     VARCHAR not null,

    created_at   timestamp with time zone not null,
    updated_at   timestamp with time zone not null
);

CREATE TABLE IF NOT EXISTS rating
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    value      BIGINT,
    user_id    BIGINT references "user" (id) on delete cascade    not null,
    article_id BIGINT references article (id) on delete cascade not null
);