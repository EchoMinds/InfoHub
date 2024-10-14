CREATE TABLE IF NOT EXISTS "user"
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "name"     VARCHAR                  NOT NULL,
    avatar_url VARCHAR,
    email      VARCHAR                  not null,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS article
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id    BIGINT REFERENCES "user" (id) ON DELETE CASCADE NOT NULL,
    title      VARCHAR                                         NOT NULL,
    text       TEXT,
    views      BIGINT DEFAULT 0,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS rating
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    value      BIGINT,
    user_id    BIGINT REFERENCES "user" (id) ON DELETE CASCADE  NOT NULL,
    article_id BIGINT REFERENCES article (id) ON DELETE CASCADE NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL

);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT       NOT NULL,
    role    VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);

ALTER TABLE "user"
    ALTER COLUMN "created_at" SET DEFAULT now(),
    ALTER COLUMN "updated_at" SET DEFAULT now();

ALTER TABLE "article"
    ALTER COLUMN "created_at" SET DEFAULT now(),
    ALTER COLUMN "updated_at" SET DEFAULT now();