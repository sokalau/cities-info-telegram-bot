CREATE DATABASE tourist_telegram_bot CHARACTER SET UTF8 COLLATE utf8_general_ci;

CREATE TABLE `tourist_telegram_bot`.`city_info` (
  `id` INT NOT NULL,
  `description` TEXT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
   CONSTRAINT test_city_info_pk PRIMARY KEY (id)
);

INSERT INTO `tourist_telegram_bot`.`city_info`
(`id`,
`description`,
`name`)
VALUES
('1',
'Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))',
'москва'),
('2',
'Электросамокаты - это тема',
'минск'),
('3',
'Нафтан - наше всё',
'новополоцк'),
('4',
'Основной достопримечательностью города Ушачи является костел Святого Лаврентия, построенный в 1913 году',
'ушачи'),
('5',
'Спортивный комплекс Неман - бомба',
'гродно');

