DROP TABLE IF EXISTS `machine`;

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
