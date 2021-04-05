CREATE DATABASE testdb;
use testdb;

CREATE TABLE `products` (
   `id` bigint NOT NULL,
   `created_date` datetime DEFAULT NULL,
   `description` varchar(255) DEFAULT NULL,
   `name` varchar(255) DEFAULT NULL,
   `price` decimal(19,2) DEFAULT NULL,
   `updated_date` datetime DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

INSERT INTO `` (`id`,`created_date`,`description`,`name`,`price`,`updated_date`) VALUES (1,'2021-04-06 01:31:20','fruit','apple',350.00,'2021-04-06 01:31:20');
INSERT INTO `` (`id`,`created_date`,`description`,`name`,`price`,`updated_date`) VALUES (2,'2021-04-06 01:32:28','fruit','grape',250.00,'2021-04-06 01:32:28');
