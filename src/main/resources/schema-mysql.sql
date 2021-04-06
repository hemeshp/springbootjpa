CREATE TABLE IF NOT EXISTS `products` (
   `id` bigint NOT NULL,
   `created_date` datetime DEFAULT NULL,
   `description` varchar(255) DEFAULT NULL,
   `name` varchar(255) DEFAULT NULL,
   `price` decimal(19,2) DEFAULT NULL,
   `updated_date` datetime DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
