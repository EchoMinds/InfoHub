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

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT       NOT NULL,
    role    VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);

ALTER TABLE "public"."user"
    ALTER COLUMN "created_at" SET DEFAULT now(),
    ALTER COLUMN "updated_at" SET DEFAULT now();