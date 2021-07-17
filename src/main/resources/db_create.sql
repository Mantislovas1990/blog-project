-- DROP TABLE IF EXISTS users;
-- DROP TABLE IF EXISTS articles;
-- DROP TABLE IF EXISTS comments;


CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY not null,
    user_name  varchar(50) UNIQUE    not null,
    full_name  varchar(100)          not null,
    email      varchar(60)           NOT NULL,
    password   varchar(20)           NOT NULL,
    userPhoto  bytea,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE articles
(
    id          bigserial PRIMARY KEY not null,
    author_id   INTEGER               NOT NULL,
    author_name varchar(100)          not null,
    title       varchar(50)           NOT NULL,
    body        TEXT                  NOT NULL,
    articleImg  bytea,
    created_at  timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users (id)
);


CREATE TABLE comments
(
    id         BIGSERIAL primary key not null,
    user_id    INTEGER               not null,
    article_id INTEGER               not null,
    comment    text                  not null,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (article_id) REFERENCES articles (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);