ALTER TABLE "public"."article"
    ALTER COLUMN "created_at" SET DEFAULT now(),
    ALTER COLUMN "updated_at" SET DEFAULT now();

CREATE TABLE IF NOT EXISTS comment
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT                   NOT NULL,
    article_id BIGINT                   NOT NULL,
    text       TEXT,
    rating     BIGINT,
    reply_to   BIGINT REFERENCES comment (id),

    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES Article (id) ON DELETE CASCADE,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

ALTER TABLE "public"."comment"
    ALTER COLUMN "created_at" SET DEFAULT now(),
    ALTER COLUMN "updated_at" SET DEFAULT now();
