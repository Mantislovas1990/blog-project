-- DROP TABLE IF EXISTS comments;
-- DROP TABLE IF EXISTS articles ;
-- DROP TABLE IF EXISTS user_role;
-- DROP TABLE IF EXISTS roles;
-- DROP TABLE IF EXISTS users;


CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY not null,
    user_name  varchar(50) UNIQUE    not null,
    first_name varchar(50)           not null,
    last_name  varchar(50)           not null,
    email      varchar(60)           NOT NULL,
    password   varchar(255)          NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE articles
(
    id         bigserial PRIMARY KEY not null,
    author_id  INTEGER               NOT NULL,
    title      varchar(50)           NOT NULL,
    body       TEXT                  NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
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
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE   NOT NULL
);

CREATE TABLE user_role
(
    user_id BIGINT REFERENCES users (id),
    role_id BIGINT REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO users(id, user_name, first_name, last_name , email, password)
VALUES (500, 'FirstUser', 'First Name', 'First Name', 'firstEmail@email.com', '1111'),
       (501, 'SecondUser', 'Second Name', 'Second Name', 'secondtEmail@email.com', '2222');


INSERT INTO user_role(user_id, role_id)
VALUES(500,1),
      (501,1);

INSERT INTO articles(id,author_id, title, body)
VALUES (301,500,'FirstUser test title', 'You only live once, but if you do it right, once is enough. — Mae West.'),
       (302,500,'FirstUser test title two', 'Your time is limited, so don’t waste it living someone else’s life. Don’t be trapped by dogma – which is living with the results of other people’s thinking. – Steve Jobs'),
       (303,501,'Second user test title','Life is not a problem to be solved, but a reality to be experienced.– Soren Kierkegaard'),
       (304,501,'Second user test title two', 'Life is like riding a bicycle. To keep your balance, you must keep moving. — Albert Einstein');

INSERT INTO comments(id,user_id, article_id, comment)
VALUES (300,500,301,'Best article i have ever seen, good job man, keep it up'),
       (301,500,302,'DAMN MAN!!, that hit me hard. Your words are helping me to get going.'),
       (302,501,301,'The fuck are you talking about? are you some monkey? Get life'),
       (303,501,301,'Nothing ir more true than words you wrote'),
       (304,501,302,'HELLL YEEEE BABY!!'),
       (305,501,303,'Was searching for something like this, my friend have some problems in life and wanted to cheer him up'),
       (306,501,304,'How is reading this in 2021?? HELLO WORLD!!')