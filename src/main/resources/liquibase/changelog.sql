
CREATE TABLE IF NOT EXISTS users
(
    id    serial PRIMARY KEY,
    vk_id integer UNIQUE NOT NULL,
    last_message bigint,
    nick_name text,
    profession text
);

CREATE TABLE IF NOT EXISTS topics
(
    id      bigserial PRIMARY KEY,
    name    text NOT NULL,
    user_id int  NOT NULL REFERENCES users (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS attachments
(
    id       bigserial PRIMARY KEY,
    name     text    NULL,
    owner_id int     NOT NULL,
    type     varchar NOT NULL,
    vk_identifier    int     NOT NULL,
    isRead   boolean NOT NULL default false,
    topic_id int     NULL REFERENCES topics (id) on delete cascade
);
