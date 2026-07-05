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
