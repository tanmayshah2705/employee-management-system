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
-- Current Database: `db_5_aug2`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `db_5_aug2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `db_5_aug2`;

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
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Ujjain',1),(2,'Indore',1),(3,'Ahmedabad',3),(4,'Gandhinagar',3),(5,'Surat',3),(6,'Mumbai',2),(7,'Pune',2),(8,'Nagpur',2),(9,'Udaipur',5),(10,'Jaipur',5),(11,'Lucknow',4),(12,'Kanpur',4),(13,'Dewas',1),(14,'Kota',5),(15,'Bengaluru',6),(16,'Bhopal',1);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (4,'Finance'),(3,'Marketing'),(2,'Purchase'),(1,'Sales');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('A101','Jatan Sharma','2003-01-12',6,'M',800000),('A102','Lalita sharma','1993-11-03',6,'F',960000),('A103','Amit Singh','1996-01-12',1,'M',1050000),('A927','Ishaan Mehta','1985-03-14',13,'M',107252),('B103','Ved dutta','2004-04-09',12,'M',1200000),('B104','Siddhartha Gautam','2000-09-19',9,'M',1700000),('B123','Varad Shukla','2001-12-03',4,'M',1200000),('B202','Priya Verma','1985-08-25',2,'F',1245000),('B874','Ira Sharma','1983-09-23',5,'F',54638),('C105','Monica Dutta','1995-01-12',5,'F',145000),('C141','Reyansh Patel','1999-11-23',9,'M',88706),('C183','Anika Chopra','2002-07-09',16,'M',90625),('C303','Rajesh Kumar','1993-03-15',3,'M',875000),('C344','Aarohi Joshi','1996-12-02',1,'M',38721),('C488','Aarohi Nair','2000-07-06',1,'M',67703),('C721','Saanvi Chopra','1980-11-14',15,'M',102521),('C822','Reyansh Joshi','2002-07-12',1,'F',49843),('D218','Aditya Desai','1976-01-11',1,'M',53213),('D223','Aarav Sharma','1994-02-19',12,'M',82835),('D283','Aditya Patel','1976-10-04',12,'F',98389),('D404','Neha Singh','1992-11-07',4,'F',960000),('E120','Aditya Gupta','1992-12-08',14,'F',80816),('E505','Suresh Reddy','1988-06-20',5,'M',1125000),('F159','Meera Sharma','1985-05-06',3,'F',56217),('F269','Aarohi Nair','1976-09-19',11,'M',44697),('F352','Aarohi Joshi','2002-05-02',4,'M',108047),('F485','Prisha Rao','1979-02-09',4,'F',105488),('F606','Anjali Mehta','1995-01-30',6,'F',740000),('F790','Sai Singh','2002-11-28',12,'F',114357),('G662','Vivaan Reddy','1989-07-17',8,'F',42852),('G707','Vikram Joshi','1991-09-18',7,'M',915000),('G729','Shaurya Reddy','1987-10-28',5,'M',58133),('H781','Aarav Rao','1982-07-04',1,'M',59912),('H808','Ritu Choudhary','1987-07-10',8,'F',1080000),('H994','Ishaan Gupta','1987-11-25',3,'F',45589),('I170','Vivaan Menon','1979-02-07',10,'M',45706),('I319','Meera Verma','1980-11-25',7,'M',59022),('I425','Aarav Verma','1985-07-28',14,'F',103067),('I581','Avni Gupta','1978-06-29',4,'M',113261),('I800','Diya Kumar','1995-02-24',4,'F',58233),('I909','Manoj Tiwari','1989-04-22',9,'M',990000),('J010','Deepika Iyer','1996-12-05',10,'F',865000),('J602','Pedro Pascal','1990-01-12',16,'M',666467),('J684','Avni Rao','1993-06-11',2,'F',75704),('J796','Aarav Desai','1993-08-18',9,'F',96354),('K111','Arjun Pandey','1994-02-28',11,'M',1035000),('K113','Myra Menon','1981-04-10',10,'M',61616),('L110','Krishna Menon','1997-08-02',16,'F',96203),('L212','Kavita Rao','1990-10-14',12,'F',975000),('L622','Aditya Desai','1984-08-09',8,'M',106629),('L639','Prisha Gupta','1983-01-03',10,'F',34287),('L721','Aditya Verma','1977-03-14',8,'F',104238),('M119','Anika Gupta','1999-06-04',7,'F',39195),('M124','Reyansh Nair','1988-04-17',8,'F',73649),('M127','Aarohi Reddy','1983-10-03',12,'M',69829),('M323','Manish Birla','1997-12-15',6,'M',1950000),('M549','Arjun Patel','1979-03-30',10,'F',113408),('M612','Shaurya Menon','1987-02-05',15,'F',107163),('M693','Prisha Chopra','1987-12-14',5,'M',34660),('M708','Shaurya Joshi','1985-09-16',7,'F',118871),('N382','Aarohi Jain','1991-07-20',1,'M',43280),('N425','Reyansh Gupta','1984-09-10',1,'M',77993),('N936','Shaurya Nair','1996-04-23',13,'F',98313),('O223','Vihaan Kumar','1999-01-05',7,'F',92870),('O306','Anaya Desai','1982-07-09',8,'M',101477),('O516','Aarav Reddy','1989-06-13',14,'M',97033),('P239','Reyansh Kapoor','1976-02-01',9,'F',100052),('P458','Krishna Singh','1995-11-09',1,'M',103670),('P471','Anaya Singh','1986-06-13',11,'F',42143),('P483','Anika Singh','1995-01-02',2,'F',115588),('Q123','Aryan Mishra','1999-12-03',6,'M',99999999),('Q335','Anika Mehta','1980-04-11',16,'F',45410),('Q610','Aditya Patel','1995-11-19',14,'F',54283),('Q772','Reyansh Nair','1998-01-19',13,'F',71723),('Q891','Arjun Sharma','1991-08-23',9,'F',57726),('qwerty','asdfghjkl','2025-04-30',9,'F',98765432),('R206','Myra Verma','1977-09-19',5,'M',38571),('R274','Aarav Reddy','1997-12-28',8,'M',32666),('R282','Anika Menon','1983-09-05',1,'F',70877),('R290','Arjun Kumar','1998-11-08',8,'F',36654),('R638','Ishaan Jain','1991-08-31',13,'M',30430),('R702','Aarav Kumar','1994-10-28',2,'M',65782),('R840','Saanvi Joshi','1979-09-15',16,'M',65793),('R904','Aarav Singh','1993-09-16',6,'M',90451),('S123','Tanvi Shah','2005-01-27',1,'F',500000),('S443','Vihaan Desai','1981-10-09',6,'M',56498),('S551','Meera Menon','1991-02-05',1,'F',94358),('S795','Ira Mehta','1981-06-26',3,'M',60327),('S833','Reyansh Desai','1979-01-07',12,'M',118454),('T144','Ishaan Mehta','1994-03-31',1,'M',82851),('T349','Anika Jain','2002-08-30',13,'M',93835),('T532','Reyansh Mehta','1994-03-04',5,'F',107885),('T767','Tom Cruise','1989-01-12',12,'M',67567675),('T898','Meera Jain','1982-03-17',9,'M',105468),('T971','Aarohi Rao','1975-12-24',8,'M',79571),('U514','Myra Rao','1983-08-06',8,'F',45994),('u573','Prabodhini','1990-01-12',7,'F',1234143),('U621','Avni Sharma','1985-09-25',7,'F',93312),('V119','Diya Chopra','1978-09-14',5,'M',92787),('V128','Vihaan Nair','1997-01-08',12,'M',81663),('V182','Aarav Verma','1980-01-26',9,'M',72054),('V326','Anaya Kumar','1990-05-23',15,'F',94952),('V332','Ishaan Patel','1980-08-22',5,'F',103532),('V670','Vivaan Sharma','1988-06-16',16,'M',85616),('W106','Avni Reddy','1978-04-27',8,'F',111892),('W182','Avni Patel','1977-09-17',7,'F',65715),('W304','Diya Patel','2001-03-13',13,'M',118933),('W468','Diya Sharma','1985-11-05',10,'F',118561),('W918','Myra Gupta','1988-06-03',16,'M',46734),('X424','Vihaan Mehta','1996-04-27',4,'F',76596),('X459','Diya Reddy','1985-12-26',9,'M',43627),('X919','Krishna Kumar','1995-07-28',6,'M',51009),('Y134','Arjun Nair','1982-01-31',13,'M',33105),('Y271','Meera Chopra','1991-08-14',6,'M',91086),('Y361','Ira Kapoor','1983-02-08',9,'F',67430),('Y526','Meera Rao','1980-04-24',4,'M',65696),('Y731','Saanvi Rao','1980-01-14',12,'F',73312),('Y872','Reyansh Jain','1976-03-31',2,'F',63007),('Z101','Pranav Sharma','1994-01-12',5,'M',200000),('Z111','Dev Patel','1990-12-12',12,'M',432234),('Z609','Reyansh Nair','1977-08-18',13,'F',87581),('Z705','Vivaan Singh','1987-01-02',12,'M',76939),('Z975','Anika Joshi','2002-09-12',14,'F',71620);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `employee_boss`
--

LOCK TABLES `employee_boss` WRITE;
/*!40000 ALTER TABLE `employee_boss` DISABLE KEYS */;
INSERT INTO `employee_boss` VALUES ('A101','B104'),('C105','B104'),('A101','B123'),('C303','C105'),('D404','C303'),('B104','I909'),('J010','I909'),('L212','I909'),('A102','M323');
/*!40000 ALTER TABLE `employee_boss` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `employee_department`
--

LOCK TABLES `employee_department` WRITE;
/*!40000 ALTER TABLE `employee_department` DISABLE KEYS */;
INSERT INTO `employee_department` VALUES ('A101',1),('B104',1),('B202',1),('C303',1),('A101',3),('A102',3),('B123',3),('D404',3),('F606',3),('A103',4),('C105',4),('E505',4);
/*!40000 ALTER TABLE `employee_department` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES (3,'Gujarat'),(6,'Karnataka'),(1,'Madhya Pradesh'),(2,'Maharashtra'),(5,'Rajasthan'),(7,'Tamil Nadu'),(4,'Uttar Pradesh');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `db_5_aug2`
--

USE `db_5_aug2`;

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

-- Dump completed on 2026-07-05 12:07:46
