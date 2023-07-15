DROP TABLE IF EXISTS `order`;

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
