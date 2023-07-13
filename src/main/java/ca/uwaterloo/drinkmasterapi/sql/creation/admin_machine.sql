CREATE TABLE `admin_machine`
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    adminId    BIGINT,
    machineId  BIGINT,
    createdAt  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (adminId) REFERENCES `admin` (id),
    FOREIGN KEY (machineId) REFERENCES `machine` (id)
);
