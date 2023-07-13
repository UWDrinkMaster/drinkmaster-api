CREATE TABLE `drink` (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       imageURL VARCHAR(255),
                       price DOUBLE NOT NULL,
                       priceCurrency VARCHAR(255) NOT NULL,
                       category VARCHAR(255),
                       description VARCHAR(255),
                       isActive BOOLEAN DEFAULT true COMMENT 'Flag indicating if the drink is active',
                       isCustomized BOOLEAN DEFAULT false,
                       userId BIGINT COMMENT 'User ID associated with the customized drink (Not NULL if isCustomized is TRUE)',
                       machineId BIGINT COMMENT 'Machine ID associated with the standard drink (Not NULL if isCustomized is False)',
                       createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);