CREATE TABLE machine (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         deviceId VARCHAR(255) NOT NULL,
                         location VARCHAR(255) NOT NULL,
                         description VARCHAR(255),
                         createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
