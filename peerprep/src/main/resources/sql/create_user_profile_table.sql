CREATE TABLE IF NOT EXISTS `user_profile` (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_group VARCHAR(255) NOT NULL,
    enabled BOOLEAN,
    locked BOOLEAN,
    activate_token VARCHAR(255) NOT NULL,
    activate_token_expire_time DATETIME,
    activation_time DATETIME,
    password_token VARCHAR(255),
    password_token_expire_time DATETIME,

    INDEX (email),
    INDEX (nickname),
    INDEX (user_group),
    INDEX (enabled),
    INDEX (locked),
    INDEX (activation_time)
) ENGINE = INNODB
  CHARACTER SET = utf8;