CREATE TABLE `admin`
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) UNIQUE,
    email       VARCHAR(255) UNIQUE,
    password    VARCHAR(255),
    isEnabled   BOOLEAN               DEFAULT true COMMENT 'Flag indicating if the user account is enabled',
    imageURL    VARCHAR(255) COMMENT 'URL of the profile picture of the user',
    signedInAt  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    createdAt   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modifiedAt  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT check_email_or_phone CHECK (
            (email IS NOT NULL AND password IS NOT NULL) OR
            (phoneNumber IS NOT NULL)
        )
);