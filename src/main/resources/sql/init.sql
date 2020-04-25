CREATE DATABASE 'tourist_telegram_bot';

CREATE TABLE `tourist_telegram_bot`.`city_info` (
  `id` INT NOT NULL,
  `description` VARCHAR(45) NULL,
  `name` VARCHAR(45) NOT NULL,
   PRIMARY KEY (`id`)
);