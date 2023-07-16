DROP TABLE IF EXISTS `drink`;

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
