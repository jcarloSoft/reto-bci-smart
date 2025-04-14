DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS phones;

CREATE TABLE users
(
    id               UUID PRIMARY KEY NOT NULL,
    name             VARCHAR(50)      NOT NULL,
    email            VARCHAR(50)      NOT NULL,
    password         VARCHAR(30)      NOT NULL,
    create_at        TIMESTAMP        NOT NULL,
    update_at        TIMESTAMP        NOT NULL,
    ultimate_session TIMESTAMP        NOT NULL
);



CREATE TABLE phones
(
    id           UUID PRIMARY KEY NOT NULL,
    phone_number VARCHAR(20)      NOT NULL,
    city_code    VARCHAR(5)       NOT NULL,
    country_code VARCHAR(5)       NOT NULL,
    user_id      UUID             NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);