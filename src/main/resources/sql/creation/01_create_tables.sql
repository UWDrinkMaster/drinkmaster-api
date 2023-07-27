CREATE TABLE `admin`
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) UNIQUE,
    email        VARCHAR(255) UNIQUE,
    password     VARCHAR(255),
    is_enabled   BOOLEAN            DEFAULT true COMMENT 'Flag indicating if the user account is enabled',
    image_url    VARCHAR(255) COMMENT 'URL of the profile picture of the user',
    signed_in_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `user`
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username                 VARCHAR(255) NOT NULL,
    phone_number             VARCHAR(255) UNIQUE,
    email                    VARCHAR(255) UNIQUE,
    password                 VARCHAR(255),
    is_enabled               BOOLEAN            DEFAULT true COMMENT 'Flag indicating if the user account is enabled',
    image_url                VARCHAR(255) COMMENT 'URL of the profile picture of the user',
    date_of_birth            DATE               DEFAULT NULL,
    last_sobriety_test_score DOUBLE             DEFAULT 0.0,
    last_sobriety_test_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    signed_in_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `machine`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id   VARCHAR(255) NOT NULL COMMENT 'Identifier for the machine device',
    private_key VARCHAR(255) NOT NULL COMMENT 'Private key used for secure authentication and connection',
    location    VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `allergen`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `ingredient`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    machine_id  BIGINT       NOT NULL,
    position    INT          NOT NULL,
    description VARCHAR(255),
    inventory   DOUBLE                DEFAULT 0.0,
    is_active   BOOLEAN               DEFAULT true COMMENT 'Flag indicating if the ingredient is active',
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (machine_id) REFERENCES `machine` (id)
);

CREATE TABLE `drink`
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    image_url      VARCHAR(255),
    price          DOUBLE       NOT NULL,
    price_currency VARCHAR(255) NOT NULL,
    category       VARCHAR(255),
    description    VARCHAR(255),
    is_active      BOOLEAN               DEFAULT true COMMENT 'Flag indicating if the drink is active',
    is_customized  BOOLEAN               DEFAULT false,
    user_id        BIGINT COMMENT 'User ID associated with the customized drink (Not NULL if is_customized is TRUE)',
    machine_id     BIGINT COMMENT 'Machine ID associated with the standard drink (Not NULL if is_customized is False)',
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    FOREIGN KEY (machine_id) REFERENCES `machine` (id)
);

CREATE TABLE `order`
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    machine_id     BIGINT       NOT NULL,
    user_id        BIGINT       NOT NULL,
    drink_id       BIGINT       NOT NULL,
    quantity       INT          NOT NULL,
    price          DOUBLE       NOT NULL,
    price_currency VARCHAR(255) NOT NULL,
    status         VARCHAR(255) NOT NULL COMMENT 'Status of the order: created, processing, completed, cancelled',
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (machine_id) REFERENCES `machine` (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    FOREIGN KEY (drink_id) REFERENCES `drink` (id)
);

CREATE TABLE `ingredient_allergen`
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    ingredient_id  BIGINT,
    allergen_id     BIGINT,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ingredient_id) REFERENCES `ingredient` (id),
    FOREIGN KEY (allergen_id) REFERENCES `allergen` (id)
);

CREATE TABLE `user_allergen`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT,
    allergen_id  BIGINT,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (allergen_id) REFERENCES allergen (id)
);

CREATE TABLE `admin_machine`
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    admin_id   BIGINT,
    machine_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES `admin` (id),
    FOREIGN KEY (machine_id) REFERENCES `machine` (id)
);

CREATE TABLE `drink_ingredient`
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    drink_id      BIGINT,
    ingredient_id BIGINT,
    quantity      DOUBLE             DEFAULT 0.0,
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (drink_id) REFERENCES `drink` (id),
    FOREIGN KEY (ingredient_id) REFERENCES `ingredient` (id)
);
