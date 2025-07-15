CREATE DATABASE  IF NOT EXISTS `poseidoncapitalsolutions`;
USE `poseidoncapitalsolutions`;


--
-- Table structure for table `bidlist`
--

DROP TABLE IF EXISTS `bidlist`;
CREATE TABLE `bidlist` (
  `bid_list_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `bid_quantity` double DEFAULT NULL,
  `account` varchar(255) NOT NULL,
  `ask` double NOT NULL,
  `ask_quantity` double NOT NULL,
  `benchmark` varchar(255) DEFAULT NULL,
  `bid` double NOT NULL,
  `bid_list_date` varbinary(255) DEFAULT NULL,
  `book` varchar(255) DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  `creation_date` varbinary(255) DEFAULT NULL,
  `creation_name` varchar(255) DEFAULT NULL,
  `deal_name` varchar(255) DEFAULT NULL,
  `deal_type` varchar(255) DEFAULT NULL,
  `revision_date` varbinary(255) DEFAULT NULL,
  `revision_name` varchar(255) DEFAULT NULL,
  `security` varchar(255) DEFAULT NULL,
  `side` varchar(255) DEFAULT NULL,
  `source_list_id` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `trader` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bid_list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `curvepoint`
--

DROP TABLE IF EXISTS `curvepoint`;
CREATE TABLE `curvepoint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `curve_id` int(11) NOT NULL,
  `term` double DEFAULT NULL,
  `value` double DEFAULT NULL,
  `as_of_date` datetime(6) DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
CREATE TABLE `rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fitch_rating` varchar(255) DEFAULT NULL,
  `moodys_rating` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `sand_p_rating` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `rulename`
--

DROP TABLE IF EXISTS `rulename`;
CREATE TABLE `rulename` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `json` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sql_part` varchar(255) DEFAULT NULL,
  `sql_str` varchar(255) DEFAULT NULL,
  `template` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `trade`
--

DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `benchmark` varchar(255) DEFAULT NULL,
  `book` varchar(255) DEFAULT NULL,
  `buy_price` double DEFAULT NULL,
  `buy_quantity` double DEFAULT NULL,
  `creation_date` varbinary(255) DEFAULT NULL,
  `creation_name` varchar(255) DEFAULT NULL,
  `deal_name` varchar(255) DEFAULT NULL,
  `deal_type` varchar(255) DEFAULT NULL,
  `revision_date` varbinary(255) DEFAULT NULL,
  `revision_name` varchar(255) DEFAULT NULL,
  `security` varchar(255) DEFAULT NULL,
  `sell_price` double DEFAULT NULL,
  `sell_quantity` double DEFAULT NULL,
  `side` varchar(255) DEFAULT NULL,
  `source_list_id` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `trade_date` varbinary(255) DEFAULT NULL,
  `trader` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

