ALTER TABLE "public"."article"
    ALTER COLUMN "created_at" SET DEFAULT now(),
    ALTER COLUMN "updated_at" SET DEFAULT now();

CREATE TABLE comment
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT                   NOT NULL,
    article_id BIGINT                   NOT NULL,
    text       TEXT,
    rating     BIGINT,

    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES Article (id) ON DELETE CASCADE,

    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

ALTER TABLE "public"."comment"
    ALTER COLUMN "created_at" SET DEFAULT now(),
    ALTER COLUMN "updated_at" SET DEFAULT now();
