CREATE TABLE drinkcategory (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       imageURL VARCHAR(255),
                       description VARCHAR(255),
                       isActive BOOLEAN DEFAULT true,
                       createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);