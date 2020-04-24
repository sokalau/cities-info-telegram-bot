CREATE DATABASE 'tg_bot';

CREATE TABLE `tg_bot`.`city_info` (
  `id` INT NOT NULL,
  `description` VARCHAR(45) NULL,
  `name` VARCHAR(45) NOT NULL,
   PRIMARY KEY (`id`)
);