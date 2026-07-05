-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: db_5_aug2
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `code` int NOT NULL AUTO_INCREMENT,
  `name` char(35) NOT NULL,
  `state_code` int NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `name` (`name`),
  KEY `state_code` (`state_code`),
  CONSTRAINT `city_ibfk_1` FOREIGN KEY (`state_code`) REFERENCES `state` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `dept_code` int NOT NULL AUTO_INCREMENT,
  `name` char(25) NOT NULL,
  PRIMARY KEY (`dept_code`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `emp_id` char(10) NOT NULL,
  `name` char(50) NOT NULL,
  `date_of_birth` date NOT NULL,
  `city_code` int NOT NULL,
  `gender` char(1) NOT NULL,
  `salary` int NOT NULL,
  PRIMARY KEY (`emp_id`),
  KEY `city_code` (`city_code`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`city_code`) REFERENCES `city` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_boss`
--

DROP TABLE IF EXISTS `employee_boss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_boss` (
  `emp_id` char(10) NOT NULL,
  `boss_emp_id` char(10) NOT NULL,
  PRIMARY KEY (`emp_id`,`boss_emp_id`),
  KEY `boss_emp_id` (`boss_emp_id`),
  CONSTRAINT `employee_boss_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`),
  CONSTRAINT `employee_boss_ibfk_2` FOREIGN KEY (`boss_emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_department`
--

DROP TABLE IF EXISTS `employee_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_department` (
  `emp_id` char(10) NOT NULL,
  `dept_code` int NOT NULL,
  PRIMARY KEY (`emp_id`,`dept_code`),
  KEY `department_id` (`dept_code`),
  CONSTRAINT `employee_department_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`),
  CONSTRAINT `employee_department_ibfk_2` FOREIGN KEY (`dept_code`) REFERENCES `department` (`dept_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `employee_details`
--

DROP TABLE IF EXISTS `employee_details`;
/*!50001 DROP VIEW IF EXISTS `employee_details`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `employee_details` AS SELECT 
 1 AS `Emp_id`,
 1 AS `Employee`,
 1 AS `D.O.B.`,
 1 AS `Gender`,
 1 AS `Salary`,
 1 AS `City`,
 1 AS `State`,
 1 AS `Department`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `state` (
  `code` int NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `employee_details`
--

/*!50001 DROP VIEW IF EXISTS `employee_details`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = cp850 */;
/*!50001 SET character_set_results     = cp850 */;
/*!50001 SET collation_connection      = cp850_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`live_5_aug`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `employee_details` AS select `employee`.`emp_id` AS `Emp_id`,`employee`.`name` AS `Employee`,`employee`.`date_of_birth` AS `D.O.B.`,'Male' AS `Gender`,`employee`.`salary` AS `Salary`,`city`.`name` AS `City`,`state`.`name` AS `State`,`department`.`name` AS `Department` from ((((`employee` join `city` on((`employee`.`city_code` = `city`.`code`))) join `state` on((`city`.`state_code` = `state`.`code`))) join `employee_department` on((`employee`.`emp_id` = `employee_department`.`emp_id`))) join `department` on((`department`.`dept_code` = `employee_department`.`dept_code`))) where (`employee`.`gender` = 'M') union select `employee`.`emp_id` AS `Emp_id`,`employee`.`name` AS `Employee`,`employee`.`date_of_birth` AS `D.O.B.`,'Female' AS `Gender`,`employee`.`salary` AS `Salary`,`city`.`name` AS `City`,`state`.`name` AS `State`,`department`.`name` AS `Department` from ((((`employee` join `city` on((`employee`.`city_code` = `city`.`code`))) join `state` on((`city`.`state_code` = `state`.`code`))) join `employee_department` on((`employee`.`emp_id` = `employee_department`.`emp_id`))) join `department` on((`department`.`dept_code` = `employee_department`.`dept_code`))) where (`employee`.`gender` = 'F') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-05 14:07:10

--
-- Seed data: major states and cities (reference data for the Add/Edit forms)
--
INSERT INTO `state` (`code`, `name`) VALUES
(1,'Andhra Pradesh'),(2,'Arunachal Pradesh'),(3,'Assam'),(4,'Bihar'),(5,'Chhattisgarh'),
(6,'Goa'),(7,'Gujarat'),(8,'Haryana'),(9,'Himachal Pradesh'),(10,'Jharkhand'),
(11,'Karnataka'),(12,'Kerala'),(13,'Madhya Pradesh'),(14,'Maharashtra'),(15,'Manipur'),
(16,'Meghalaya'),(17,'Mizoram'),(18,'Nagaland'),(19,'Odisha'),(20,'Punjab'),
(21,'Rajasthan'),(22,'Sikkim'),(23,'Tamil Nadu'),(24,'Telangana'),(25,'Tripura'),
(26,'Uttar Pradesh'),(27,'Uttarakhand'),(28,'West Bengal'),(29,'Delhi'),(30,'Jammu and Kashmir'),
(31,'Ladakh'),(32,'Puducherry'),(33,'Chandigarh'),(34,'Andaman and Nicobar');

INSERT INTO `city` (`name`, `state_code`) VALUES
('Visakhapatnam',1),('Vijayawada',1),('Guntur',1),('Nellore',1),('Tirupati',1),
('Itanagar',2),
('Guwahati',3),('Dibrugarh',3),('Silchar',3),
('Patna',4),('Gaya',4),('Bhagalpur',4),('Muzaffarpur',4),
('Raipur',5),('Bhilai',5),('Bilaspur',5),
('Panaji',6),('Margao',6),('Vasco da Gama',6),
('Ahmedabad',7),('Surat',7),('Vadodara',7),('Rajkot',7),('Gandhinagar',7),
('Faridabad',8),('Gurugram',8),('Panipat',8),('Ambala',8),('Karnal',8),
('Shimla',9),('Dharamshala',9),('Solan',9),
('Ranchi',10),('Jamshedpur',10),('Dhanbad',10),('Bokaro',10),
('Bengaluru',11),('Mysuru',11),('Mangaluru',11),('Hubballi',11),('Belagavi',11),
('Thiruvananthapuram',12),('Kochi',12),('Kozhikode',12),('Thrissur',12),('Kollam',12),
('Bhopal',13),('Indore',13),('Jabalpur',13),('Gwalior',13),('Ujjain',13),
('Mumbai',14),('Pune',14),('Nagpur',14),('Nashik',14),('Aurangabad',14),('Thane',14),
('Imphal',15),
('Shillong',16),
('Aizawl',17),
('Kohima',18),('Dimapur',18),
('Bhubaneswar',19),('Cuttack',19),('Rourkela',19),('Puri',19),
('Ludhiana',20),('Amritsar',20),('Jalandhar',20),('Patiala',20),('Bathinda',20),
('Jaipur',21),('Jodhpur',21),('Udaipur',21),('Kota',21),('Ajmer',21),('Bikaner',21),
('Gangtok',22),
('Chennai',23),('Coimbatore',23),('Madurai',23),('Tiruchirappalli',23),('Salem',23),('Tirunelveli',23),
('Hyderabad',24),('Warangal',24),('Nizamabad',24),('Karimnagar',24),
('Agartala',25),
('Lucknow',26),('Kanpur',26),('Agra',26),('Varanasi',26),('Prayagraj',26),('Ghaziabad',26),('Noida',26),('Meerut',26),
('Dehradun',27),('Haridwar',27),('Nainital',27),('Rishikesh',27),
('Kolkata',28),('Howrah',28),('Durgapur',28),('Asansol',28),('Siliguri',28),
('New Delhi',29),('Delhi',29),
('Srinagar',30),('Jammu',30),
('Leh',31),
('Puducherry',32),
('Chandigarh',33),
('Port Blair',34);
