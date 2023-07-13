CREATE TABLE `allergy` (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description VARCHAR(255),
                         createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);