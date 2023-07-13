CREATE TABLE `user` (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      isEnabled BOOLEAN DEFAULT true COMMENT 'Flag indicating if the user account is enabled',
                      imageURL VARCHAR(255) COMMENT 'URL of the profile picture of the user',
                      dateOfBirth DATE,
                      lastSobrietyTestScore DOUBLE,
                      lastSobrietyTestAt TIMESTAMP,
                      signedInAt TIMESTAMP,
                      createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      modifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);