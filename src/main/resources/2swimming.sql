CREATE DATABASE  IF NOT EXISTS `swimming` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `swimming`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: swimming
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `family`
--

DROP TABLE IF EXISTS `family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `family` (
  `family_id` int NOT NULL,
  `primary_user_email` varchar(45) DEFAULT NULL,
  `user_email` varchar(45) NOT NULL,
  PRIMARY KEY (`family_id`),
  KEY `fk_family_user1_idx` (`user_email`),
  CONSTRAINT `fk_family_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family`
--

LOCK TABLES `family` WRITE;
/*!40000 ALTER TABLE `family` DISABLE KEYS */;
INSERT INTO `family` VALUES (1,'marie.fiskbak@gmail.com',''),(2,'s@s.dk',''),(3,NULL,''),(4,NULL,''),(5,NULL,'');
/*!40000 ALTER TABLE `family` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `family_id` int DEFAULT NULL,
  `message` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`message_id`),
  KEY `fk_messages_family1_idx` (`family_id`),
  CONSTRAINT `fk_messages_family1` FOREIGN KEY (`family_id`) REFERENCES `family` (`family_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (4,2,'Marie Fiskbæk har købt 9 billetter fra dig til den 2022-06-17. Du skulle gerne have modtaget 135 kr på Mobile Pay.'),(13,2,'Marie Fiskbæk har købt 13 billetter fra dig til den 2022-05-20. Du skulle gerne have modtaget 195 kr på Mobile Pay.'),(14,2,'Marie Fiskbæk har købt 13 billetter fra dig til den 2022-05-20. Du skulle gerne have modtaget 195 kr på Mobile Pay.'),(15,1,'Marie Fiskbæk har købt 1 billetter fra dig til den 2022-06-03. Du skulle gerne have modtaget 15 kr på Mobile Pay.');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration` (
  `registration_id` int NOT NULL,
  `family_id` int DEFAULT NULL,
  `team_id` varchar(30) DEFAULT NULL,
  `ticket_amount` int DEFAULT NULL,
  PRIMARY KEY (`registration_id`),
  KEY `fk_registration_family1_idx` (`family_id`),
  KEY `fk_registration_team1_idx` (`team_id`),
  CONSTRAINT `fk_registration_family1` FOREIGN KEY (`family_id`) REFERENCES `family` (`family_id`),
  CONSTRAINT `fk_registration_team1` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES (1,1,'lige uge fre 10-11',3),(2,2,'lige uge fre 10-11',3),(3,2,'ulige uge fre 10-11',3);
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swimday`
--

DROP TABLE IF EXISTS `swimday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `swimday` (
  `swimdate` date NOT NULL,
  `week_no` int DEFAULT NULL,
  `team_id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`swimdate`),
  KEY `fk_tickets_registration1_idx` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swimday`
--

LOCK TABLES `swimday` WRITE;
/*!40000 ALTER TABLE `swimday` DISABLE KEYS */;
INSERT INTO `swimday` VALUES ('2022-05-20',20,'lige fre 10-11'),('2022-06-03',22,'lige fre 10-11'),('2022-06-10',23,'ulige fre 10-11'),('2022-06-17',24,'lige fre 10-11'),('2022-06-24',25,'ulige fre 10-11');
/*!40000 ALTER TABLE `swimday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swimdaytickets`
--

DROP TABLE IF EXISTS `swimdaytickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `swimdaytickets` (
  `swimdate` date NOT NULL,
  `family_id` int NOT NULL,
  `current_ticket_amount` int DEFAULT '0',
  `tickets_for_sale` int DEFAULT '0',
  `reserved_tickets` int DEFAULT '0',
  `timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`swimdate`,`family_id`),
  KEY `fk_swimdaytickets_family1_idx` (`family_id`),
  CONSTRAINT `fk_swimdaytickets_family1` FOREIGN KEY (`family_id`) REFERENCES `family` (`family_id`),
  CONSTRAINT `fk_swimdaytickets_swimday1` FOREIGN KEY (`swimdate`) REFERENCES `swimday` (`swimdate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swimdaytickets`
--

LOCK TABLES `swimdaytickets` WRITE;
/*!40000 ALTER TABLE `swimdaytickets` DISABLE KEYS */;
INSERT INTO `swimdaytickets` VALUES ('2022-05-20',1,26,0,0,'2022-05-02 23:09:27'),('2022-05-20',2,0,-13,0,'2022-05-02 21:51:27'),('2022-06-03',1,11,1,0,'2022-05-02 23:09:29'),('2022-06-03',2,0,0,0,'2022-05-02 21:51:29'),('2022-06-10',1,3,0,0,'2022-05-02 23:09:31'),('2022-06-10',2,0,0,0,'2022-05-01 12:47:11'),('2022-06-17',1,0,9,0,'2022-05-03 00:13:34'),('2022-06-17',2,0,0,0,'2022-05-01 17:53:20'),('2022-06-24',1,3,0,0,'2022-05-02 23:09:34'),('2022-06-24',2,0,0,0,'2022-05-02 21:51:32');
/*!40000 ALTER TABLE `swimdaytickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `team_id` varchar(30) NOT NULL,
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES ('lige uge fre 10-11'),('ulige uge fre 10-11');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `email` varchar(45) NOT NULL,
  `phone_no` int DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `family_id` int DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `primary_user` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`email`),
  KEY `fk_user_family1_idx` (`family_id`),
  CONSTRAINT `fk_user_family1` FOREIGN KEY (`family_id`) REFERENCES `family` (`family_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('jontoftjensen@gmail.com',61787050,'Jon Toft-Jensen','user',1,'1234',NULL),('marie.fiskbak@gmail.com',21861308,'Marie Fiskbæk','admin',1,'1234','yes'),('s@s.dk',55228882,'Sietske','user',2,'1234','yes');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-03  9:19:38
