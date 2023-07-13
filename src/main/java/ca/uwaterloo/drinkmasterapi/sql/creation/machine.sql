CREATE TABLE `machine`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    deviceId    VARCHAR(255) NOT NULL COMMENT 'Identifier for the machine device',
    privateKey  VARCHAR(255) NOT NULL COMMENT 'Private key used for secure authentication and connection',
    location    VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    createdAt   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modifiedAt  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
