-- DROP TABLE IF EXISTS comments;
-- DROP TABLE IF EXISTS articles ;
-- DROP TABLE IF EXISTS user_role;
-- DROP TABLE IF EXISTS roles;
-- DROP TABLE IF EXISTS users;


CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY not null,
    user_name  varchar(50) UNIQUE    not null,
    first_name  varchar(50)          not null,
    last_name  varchar(50)          not null,
    email      varchar(60)           NOT NULL,
    password   varchar(255)           NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE articles
(
    id          bigserial PRIMARY KEY not null,
    author_id   INTEGER               NOT NULL,
    title       varchar(50)           NOT NULL,
    body        TEXT                  NOT NULL,
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

CREATE TABLE roles
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE user_role
(
    user_id BIGINT REFERENCES "users"(id),
    role_id BIGINT REFERENCES roles(id)
);

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN');

