CREATE TABLE IF NOT EXISTS AccountSuspensions
(
    id               BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id          BIGINT REFERENCES "user" (id) ON DELETE CASCADE NOT NULL,
    admin_id         BIGINT REFERENCES "user" (id) ON DELETE CASCADE NOT NULL,
    ban_time         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    reason_ban       VARCHAR(255),
    is_banned        BOOLEAN,
    is_permanent_ban BOOLEAN,

    created_at       TIMESTAMP WITH TIME ZONE                        NOT NULL,
    updated_at       TIMESTAMP WITH TIME ZONE                        NOT NULL
);
