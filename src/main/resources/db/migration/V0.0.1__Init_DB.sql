CREATE TABLE IF NOT EXISTS article
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title      VARCHAR                  NOT NULL,
    views      BIGINT,

    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

CREATE TABLE IF NOT EXISTS "user"
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "name"     VARCHAR                  NOT NULL,
    avatar_url VARCHAR,
    email      VARCHAR                  not null,
    password   VARCHAR                  not null,

    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

CREATE TABLE IF NOT EXISTS rating
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    value      BIGINT,
    user_id    BIGINT references "user" (id) on delete cascade  not null,
    article_id BIGINT references article (id) on delete cascade not null
);

CREATE TABLE IF NOT EXISTS community
(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY

);

CREATE TABLE IF NOT EXISTS community_role
(
    id           BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id      BIGINT REFERENCES "user" (id)    NOT NULL,
    community_id BIGINT REFERENCES community (id) NOT NULL,
    type         VARCHAR(50)                      NOT NULL,
    UNIQUE (user_id, community_id, type)
);

CREATE TABLE IF NOT EXISTS website_role
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id    BIGINT REFERENCES "user" (id)  NOT NULL,
    article_id BIGINT REFERENCES article (id) NOT NULL,
    type       VARCHAR(50)                    NOT NULL,
    UNIQUE (user_id, article_id, type)
);

