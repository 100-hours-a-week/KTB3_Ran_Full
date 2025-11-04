CREATE TABLE user
(
    user_id  BIGINT       NOT NULL,
    email    VARCHAR(255) NULL,
    username VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);