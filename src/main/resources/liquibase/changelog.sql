
CREATE TABLE IF NOT EXISTS users
(
    id    serial PRIMARY KEY,
    vk_id integer UNIQUE NOT NULL,
    last_message bigint,
    nick_name text,
    profession text,
    frequency_remember text,
    not_read_count text
);

CREATE TABLE IF NOT EXISTS reminders
(
    id      serial PRIMARY KEY,
    day_number int NOT NULL CHECK (day_number < 8 and day_number >0),
    time time NOT NULL,
    user_id int  NOT NULL REFERENCES users (id) on delete cascade
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
