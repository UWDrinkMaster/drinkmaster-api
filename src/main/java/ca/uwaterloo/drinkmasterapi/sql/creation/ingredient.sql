DROP TABLE IF EXISTS `ingredient`;

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
