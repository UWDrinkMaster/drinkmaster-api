CREATE TABLE drink (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       categoryId BIGINT NOT NULL,
                       imageURL VARCHAR(255),
                       price DOUBLE NOT NULL,
                       priceCurrency VARCHAR(255) NOT NULL,
                       description VARCHAR(255),
                       isActive BOOLEAN DEFAULT true,
                       isCustomized BOOLEAN DEFAULT false,
                       userId BIGINT,
                       machineId BIGINT,
                       createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);