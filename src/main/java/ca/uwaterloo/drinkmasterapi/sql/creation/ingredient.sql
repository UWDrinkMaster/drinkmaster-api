CREATE TABLE `ingredient`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    machineId   BIGINT       NOT NULL,
    position    INT          NOT NULL,
    description VARCHAR(255),
    inventory   DOUBLE                DEFAULT 0.0,
    isActive    BOOLEAN               DEFAULT true COMMENT 'Flag indicating if the ingredient is active',
    createdAt   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modifiedAt  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (machineId) REFERENCES `machine` (id)
);