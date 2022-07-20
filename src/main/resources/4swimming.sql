CREATE DATABASE  IF NOT EXISTS `swimming` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `swimming`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: swimming
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
  `user_email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`family_id`),
  KEY `fk_family_user1_idx` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family`
--

LOCK TABLES `family` WRITE;
/*!40000 ALTER TABLE `family` DISABLE KEYS */;
INSERT INTO `family` VALUES (1,'marie.fiskbak@gmail.com',''),(2,'kcgorter@gmail.com',''),(3,'helga@stovring.se',''),(4,'sissel_moody@hotmail.com',''),(5,'cw.camillawagner@gmail.com ',''),(6,'mvdjacobsen@gmail.com ',NULL),(7,'fam.stam@outlook.com',NULL),(8,'kristbergmann@gmail.com',NULL),(9,'rvmkiks@gmail.com',NULL),(10,'t_holst_l@hotmail.com',NULL),(11,'ditte.frost@cool.dk',NULL),(12,'dittedibber@hotmail.com',NULL),(13,'kia.christensen@hotmail.com',NULL),(14,'bornper@gmail.com',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration` (
  `registration_id` int NOT NULL AUTO_INCREMENT,
  `family_id` int DEFAULT NULL,
  `team_id` varchar(30) DEFAULT NULL,
  `ticket_amount` int DEFAULT NULL,
  PRIMARY KEY (`registration_id`),
  KEY `fk_registration_family1_idx` (`family_id`),
  KEY `fk_registration_team1_idx` (`team_id`),
  CONSTRAINT `fk_registration_family1` FOREIGN KEY (`family_id`) REFERENCES `family` (`family_id`),
  CONSTRAINT `fk_registration_team1` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES (5,1,'lige uge fre 9-10',4),(6,1,'lige uge fre 10-11',3),(7,2,'lige uge fre 10-11',4),(8,2,'ulige uge fre 10-11',4);
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swimday`
--

DROP TABLE IF EXISTS `swimday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `swimday` (
  `swimdate` datetime NOT NULL,
  `week_no` int DEFAULT NULL,
  `team_id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`swimdate`),
  KEY `fk_tickets_registration1_idx` (`team_id`),
  CONSTRAINT `fk_swimday_team1` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swimday`
--

LOCK TABLES `swimday` WRITE;
/*!40000 ALTER TABLE `swimday` DISABLE KEYS */;
INSERT INTO `swimday` VALUES ('2022-08-12 09:00:00',32,'lige uge fre 9-10'),('2022-08-12 10:00:00',32,'lige uge fre 10-11'),('2022-08-19 09:00:00',33,'ulige uge fre 9-10'),('2022-08-19 10:00:00',33,'ulige uge fre 10-11'),('2022-08-26 09:00:00',34,'lige uge fre 9-10'),('2022-08-26 10:00:00',34,'lige uge fre 10-11'),('2022-09-02 09:00:00',35,'ulige uge fre 9-10'),('2022-09-02 10:00:00',35,'ulige uge fre 10-11'),('2022-09-09 09:00:00',36,'lige uge fre 9-10'),('2022-09-09 10:00:00',36,'lige uge fre 10-11'),('2022-09-16 09:00:00',37,'ulige uge fre 9-10'),('2022-09-16 10:00:00',37,'ulige uge fre 10-11'),('2022-09-23 09:00:00',38,'lige uge fre 9-10'),('2022-09-23 10:00:00',38,'lige uge fre 10-11'),('2022-09-30 09:00:00',39,'ulige uge fre 9-10'),('2022-09-30 10:00:00',39,'ulige uge fre 10-11'),('2022-10-07 09:00:00',40,'lige uge fre 9-10'),('2022-10-07 10:00:00',40,'lige uge fre 10-11'),('2022-10-14 09:00:00',41,'ulige uge fre 9-10'),('2022-10-14 10:00:00',41,'ulige uge fre 10-11'),('2022-10-28 09:00:00',43,'ulige uge fre 9-10'),('2022-10-28 10:00:00',43,'ulige uge fre 10-11'),('2022-11-04 09:00:00',44,'lige uge fre 9-10'),('2022-11-04 10:00:00',44,'lige uge fre 10-11'),('2022-11-11 09:00:00',45,'ulige uge fre 9-10'),('2022-11-11 10:00:00',45,'ulige uge fre 10-11'),('2022-11-18 09:00:00',46,'lige uge fre 9-10'),('2022-11-18 10:00:00',46,'lige uge fre 10-11'),('2022-11-25 09:00:00',47,'ulige uge fre 9-10'),('2022-11-25 10:00:00',47,'ulige uge fre 10-11'),('2022-12-02 09:00:00',48,'lige uge fre 9-10'),('2022-12-02 10:00:00',48,'lige uge fre 10-11'),('2022-12-09 09:00:00',49,'ulige uge fre 9-10'),('2022-12-09 10:00:00',49,'ulige uge fre 10-11'),('2022-12-16 09:00:00',50,'lige uge fre 9-10'),('2022-12-16 10:00:00',50,'lige uge fre 10-11'),('2022-12-30 09:00:00',52,'lige uge fre 9-10'),('2022-12-30 10:00:00',52,'lige uge fre 10-11'),('2023-01-06 09:00:00',1,'ulige uge fre 9-10'),('2023-01-06 10:00:00',1,'ulige uge fre 10-11'),('2023-01-13 09:00:00',2,'lige uge fre 9-10'),('2023-01-13 10:00:00',2,'lige uge fre 10-11'),('2023-01-20 09:00:00',3,'ulige uge fre 9-10'),('2023-01-20 10:00:00',3,'ulige uge fre 10-11'),('2023-01-27 09:00:00',4,'lige uge fre 9-10'),('2023-01-27 10:00:00',4,'lige uge fre 10-11'),('2023-02-03 09:00:00',5,'ulige uge fre 9-10'),('2023-02-03 10:00:00',5,'ulige uge fre 10-11'),('2023-02-10 09:00:00',6,'lige uge fre 9-10'),('2023-02-10 10:00:00',6,'lige uge fre 10-11'),('2023-02-17 09:00:00',7,'ulige uge fre 9-10'),('2023-02-17 10:00:00',7,'ulige uge fre 10-11'),('2023-03-03 09:00:00',9,'ulige uge fre 9-10'),('2023-03-03 10:00:00',9,'ulige uge fre 10-11'),('2023-03-10 09:00:00',10,'lige uge fre 9-10'),('2023-03-10 10:00:00',10,'lige uge fre 10-11'),('2023-03-17 09:00:00',11,'ulige uge fre 9-10'),('2023-03-17 10:00:00',11,'ulige uge fre 10-11'),('2023-03-24 09:00:00',12,'lige uge fre 9-10'),('2023-03-24 10:00:00',12,'lige uge fre 10-11'),('2023-03-31 09:00:00',13,'ulige uge fre 9-10'),('2023-03-31 10:00:00',13,'ulige uge fre 10-11'),('2023-04-14 09:00:00',15,'ulige uge fre 9-10'),('2023-04-14 10:00:00',15,'ulige uge fre 10-11'),('2023-04-21 09:00:00',16,'lige uge fre 9-10'),('2023-04-21 10:00:00',16,'lige uge fre 10-11'),('2023-04-28 09:00:00',17,'ulige uge fre 9-10'),('2023-04-28 10:00:00',17,'ulige uge fre 10-11'),('2023-05-12 09:00:00',19,'lige uge fre 9-10'),('2023-05-12 10:00:00',19,'lige uge fre 10-11'),('2023-05-26 09:00:00',21,'ulige uge fre 9-10'),('2023-05-26 10:00:00',21,'ulige uge fre 10-11'),('2023-06-02 09:00:00',22,'lige uge fre 9-10'),('2023-06-02 10:00:00',22,'lige uge fre 10-11'),('2023-06-09 09:00:00',23,'ulige uge fre 9-10'),('2023-06-09 10:00:00',23,'ulige uge fre 10-11'),('2023-06-16 09:00:00',24,'lige uge fre 9-10'),('2023-06-16 10:00:00',24,'lige uge fre 10-11'),('2023-06-23 09:00:00',25,'lige uge fre 9-10'),('2023-06-23 10:00:00',25,'lige uge fre 10-11');
/*!40000 ALTER TABLE `swimday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swimdaytickets`
--

DROP TABLE IF EXISTS `swimdaytickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `swimdaytickets` (
  `swimdate` datetime NOT NULL,
  `family_id` int NOT NULL,
  `current_ticket_amount` int unsigned DEFAULT '0',
  `tickets_for_sale` int unsigned DEFAULT '0',
  `timestamp_sale` datetime DEFAULT NULL,
  `reserved_tickets` int unsigned DEFAULT '0',
  `wants_tickets` int unsigned DEFAULT '0',
  `timestamp_wants` datetime DEFAULT NULL,
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
INSERT INTO `swimdaytickets` VALUES ('2022-08-12 09:00:00',1,4,0,'2022-07-20 16:26:11',0,0,NULL),('2022-08-12 09:00:00',2,0,0,'2022-07-17 22:25:23',0,0,NULL),('2022-08-12 09:00:00',3,0,0,'2022-07-17 22:24:51',0,0,NULL),('2022-08-12 10:00:00',1,3,0,NULL,0,0,NULL),('2022-08-12 10:00:00',2,4,0,NULL,0,0,NULL),('2022-08-19 10:00:00',2,4,0,NULL,0,0,NULL),('2022-08-26 09:00:00',1,4,0,NULL,0,0,NULL),('2022-08-26 10:00:00',1,3,0,NULL,0,0,NULL),('2022-08-26 10:00:00',2,4,0,NULL,0,0,NULL),('2022-09-02 10:00:00',2,4,0,NULL,0,0,NULL),('2022-09-09 09:00:00',1,4,0,NULL,0,0,NULL),('2022-09-09 10:00:00',1,3,0,NULL,0,0,NULL),('2022-09-09 10:00:00',2,4,0,NULL,0,0,NULL),('2022-09-16 10:00:00',2,4,0,NULL,0,0,NULL),('2022-09-23 09:00:00',1,4,0,NULL,0,0,NULL),('2022-09-23 10:00:00',1,3,0,NULL,0,0,NULL),('2022-09-23 10:00:00',2,4,0,NULL,0,0,NULL),('2022-09-30 10:00:00',2,4,0,NULL,0,0,NULL),('2022-10-07 09:00:00',1,4,0,NULL,0,0,NULL),('2022-10-07 10:00:00',1,3,0,NULL,0,0,NULL),('2022-10-07 10:00:00',2,4,0,NULL,0,0,NULL),('2022-10-14 10:00:00',2,4,0,NULL,0,0,NULL),('2022-10-28 10:00:00',2,4,0,NULL,0,0,NULL),('2022-11-04 09:00:00',1,4,0,NULL,0,0,NULL),('2022-11-04 10:00:00',1,3,0,NULL,0,0,NULL),('2022-11-04 10:00:00',2,4,0,NULL,0,0,NULL),('2022-11-11 10:00:00',2,4,0,NULL,0,0,NULL),('2022-11-18 09:00:00',1,4,0,NULL,0,0,NULL),('2022-11-18 10:00:00',1,3,0,NULL,0,0,NULL),('2022-11-18 10:00:00',2,4,0,NULL,0,0,NULL),('2022-11-25 10:00:00',2,4,0,NULL,0,0,NULL),('2022-12-02 09:00:00',1,4,0,NULL,0,0,NULL),('2022-12-02 10:00:00',1,3,0,NULL,0,0,NULL),('2022-12-02 10:00:00',2,4,0,NULL,0,0,NULL),('2022-12-09 10:00:00',2,4,0,NULL,0,0,NULL),('2022-12-16 09:00:00',1,4,0,NULL,0,0,NULL),('2022-12-16 10:00:00',1,3,0,NULL,0,0,NULL),('2022-12-16 10:00:00',2,4,0,NULL,0,0,NULL),('2022-12-30 09:00:00',1,4,0,NULL,0,0,NULL),('2022-12-30 10:00:00',1,3,0,NULL,0,0,NULL),('2022-12-30 10:00:00',2,4,0,NULL,0,0,NULL),('2023-01-06 10:00:00',2,4,0,NULL,0,0,NULL),('2023-01-13 09:00:00',1,4,0,NULL,0,0,NULL),('2023-01-13 10:00:00',1,3,0,NULL,0,0,NULL),('2023-01-13 10:00:00',2,4,0,NULL,0,0,NULL),('2023-01-20 10:00:00',2,4,0,NULL,0,0,NULL),('2023-01-27 09:00:00',1,4,0,NULL,0,0,NULL),('2023-01-27 10:00:00',1,3,0,NULL,0,0,NULL),('2023-01-27 10:00:00',2,4,0,NULL,0,0,NULL),('2023-02-03 10:00:00',2,4,0,NULL,0,0,NULL),('2023-02-10 09:00:00',1,4,0,NULL,0,0,NULL),('2023-02-10 10:00:00',1,3,0,NULL,0,0,NULL),('2023-02-10 10:00:00',2,4,0,NULL,0,0,NULL),('2023-02-17 10:00:00',2,4,0,NULL,0,0,NULL),('2023-03-03 10:00:00',2,4,0,NULL,0,0,NULL),('2023-03-10 09:00:00',1,4,0,NULL,0,0,NULL),('2023-03-10 10:00:00',1,3,0,NULL,0,0,NULL),('2023-03-10 10:00:00',2,4,0,NULL,0,0,NULL),('2023-03-17 10:00:00',2,4,0,NULL,0,0,NULL),('2023-03-24 09:00:00',1,4,0,NULL,0,0,NULL),('2023-03-24 10:00:00',1,3,0,NULL,0,0,NULL),('2023-03-24 10:00:00',2,4,0,NULL,0,0,NULL),('2023-03-31 10:00:00',2,4,0,NULL,0,0,NULL),('2023-04-14 10:00:00',2,4,0,NULL,0,0,NULL),('2023-04-21 09:00:00',1,4,0,NULL,0,0,NULL),('2023-04-21 10:00:00',1,3,0,NULL,0,0,NULL),('2023-04-21 10:00:00',2,4,0,NULL,0,0,NULL),('2023-04-28 10:00:00',2,4,0,NULL,0,0,NULL),('2023-05-12 09:00:00',1,4,0,NULL,0,0,NULL),('2023-05-12 10:00:00',1,3,0,NULL,0,0,NULL),('2023-05-12 10:00:00',2,4,0,NULL,0,0,NULL),('2023-05-26 10:00:00',2,4,0,NULL,0,0,NULL),('2023-06-02 09:00:00',1,4,0,NULL,0,0,NULL),('2023-06-02 10:00:00',1,3,0,NULL,0,0,NULL),('2023-06-02 10:00:00',2,4,0,NULL,0,0,NULL),('2023-06-09 10:00:00',2,4,0,NULL,0,0,NULL),('2023-06-16 09:00:00',1,4,0,NULL,0,0,NULL),('2023-06-16 10:00:00',1,3,0,NULL,0,0,NULL),('2023-06-16 10:00:00',2,4,0,NULL,0,0,NULL),('2023-06-23 09:00:00',1,4,0,NULL,0,0,NULL),('2023-06-23 10:00:00',1,3,0,NULL,0,0,NULL),('2023-06-23 10:00:00',2,4,0,NULL,0,0,NULL);
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
INSERT INTO `team` VALUES ('lige uge fre 10-11'),('lige uge fre 9-10'),('ulige uge fre 10-11'),('ulige uge fre 9-10');
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
  `phone_no` varchar(70) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT 'user',
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
INSERT INTO `user` VALUES ('bornper@gmail.com','53794041','Per Andersson','user',14,'perswim','yes'),('cw.camillawagner@gmail.com ','28518958','Camilla Wagner','user',5,'camillaswim','yes'),('ditte.frost@cool.dk','22405259','Ditte Frost','user',11,'ditteswim','yes'),('dittedibber@hotmail.com','53811121','Ditte Dibber','user',12,'ditteswim','yes'),('fam.stam@outlook.com','55222345','Mirjam Stam','user',7,'mirjamswim','yes'),('helga@stovring.se','31519793','Helga Stövring-Nielsen','user',3,'helgaswim','yes'),('jontoftjensen@gmail.com','61787050','Jon Toft-Jensen','user',1,'jonswim','no'),('kcgorter@gmail.com','55228882','Sietske Klasina Gorter','user',2,'sietskeswim','yes'),('kia.christensen@hotmail.com','27515145','Kia Christensen','user',13,'kiaswim','yes'),('kimtendamme@gmail.com','53794041','Kim Ten Damme','user',14,'kimswim','no'),('kristbergmann@gmail.com','52175244','Siine Bergmann ','user',8,'siineswim','yes'),('marie.fiskbak@gmail.com','21861308','Marie Fiskbæk','admin',1,'mariebffswim','yes'),('mvdjacobsen@gmail.com ','24828331','Majken Jacobsen','user',6,'majkenswim','yes'),('rvmkiks@gmail.com','93801985','Roman Martins','user',9,'romanswim','yes'),('sissel_moody@hotmail.com','22264659','Sissel Moody','user',4,'sisselswim','yes'),('t_holst_l@hotmail.com','61650895','Tobias Holst Ladefoged','user',10,'tobiasswim','yes');
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

-- Dump completed on 2022-07-20 19:18:29
