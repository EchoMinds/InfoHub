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
