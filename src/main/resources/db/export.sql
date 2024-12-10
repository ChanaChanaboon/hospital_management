-- MySQL dump 10.13  Distrib 9.0.1, for macos14.7 (x86_64)
--
-- Host: localhost    Database: hospital_management
-- ------------------------------------------------------
-- Server version	9.0.1

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
-- Table structure for table `appointment`
--
-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS=0;

-- Drop existing tables

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `appointmentID` int NOT NULL AUTO_INCREMENT,
  `patientID` int DEFAULT NULL,
  `doctorID` int DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `time` time DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`appointmentID`),
  KEY `fk_patient_appointment` (`patientID`),
  KEY `appointment_ibfk_2` (`doctorID`),
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`doctorID`) REFERENCES `doctor` (`doctorID`) ON DELETE SET NULL,
  CONSTRAINT `fk_patient_appointment` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,1,1,'2024-03-01 10:00:00.000000','10:00:00','Pending'),(2,2,2,'2024-03-02 11:30:00.000000','11:30:00','Pending'),(3,3,3,'2024-03-03 09:15:00.000000','09:15:00','Completed'),(4,4,4,'2024-03-04 14:00:00.000000','14:00:00','Cancelled'),(5,5,5,'2024-03-05 08:45:00.000000','08:45:00','Pending'),(6,6,6,'2024-03-06 13:00:00.000000','13:00:00','Completed'),(7,7,7,'2024-03-07 15:30:00.000000','15:30:00','Pending'),(8,8,8,'2024-03-08 16:00:00.000000','16:00:00','Completed'),(9,9,9,'2024-03-09 11:00:00.000000','11:00:00','Cancelled'),(10,10,10,'2024-03-10 10:30:00.000000','10:30:00','Cancelled'),(11,11,1,'2024-12-11 00:00:00.000000','00:22:00','Pending');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Department`
--

DROP TABLE IF EXISTS `Department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Department` (
  `deptID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `floor` int DEFAULT NULL,
  PRIMARY KEY (`deptID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Department`
--

LOCK TABLES `Department` WRITE;
/*!40000 ALTER TABLE `Department` DISABLE KEYS */;
INSERT INTO `Department` VALUES (1,'Cardiology',1),(2,'Neurology',2),(3,'Pediatrics',3),(4,'Orthopedics',4),(5,'Dermatology',5),(6,'Oncology',6),(7,'Psychiatry',7),(8,'Anesthesiology',8),(9,'Radiology',9),(10,'General Surgery',10);
/*!40000 ALTER TABLE `Department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `doctorID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `specialty` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `deptID` int DEFAULT NULL,
  PRIMARY KEY (`doctorID`),
  KEY `FKm2hpf4bma0dbmenvjwr2k2ykr` (`deptID`),
  CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`deptID`) REFERENCES `Department` (`deptID`),
  CONSTRAINT `FKm2hpf4bma0dbmenvjwr2k2ykr` FOREIGN KEY (`deptID`) REFERENCES `department` (`deptID`) ON DELETE SET NULL,
  CONSTRAINT `FKsyud25dnw1i4bhgu2c0ta712c` FOREIGN KEY (`deptID`) REFERENCES `Department` (`deptID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'John Smith','Cardiology','1234567890',1),(2,'Emily Davis','Neurology','9876543210',2),(3,'Michael Johnson','Pediatrics','2345678901',3),(4,'Sophia Garcia','Orthopedics','3456789012',4),(5,'William Martinez','Dermatology','4567890123',5),(6,'Isabella Wilson','Oncology','5678901234',6),(7,'Oliver Jones','Psychiatry','6789012345',7),(8,'Emma Clark','Anesthesiology','7890123456',8),(9,'James Lopez','Radiology','8901234567',9),(10,'Charlotte Smith','General Surgery','9012345678',10);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Patient`
--

DROP TABLE IF EXISTS `Patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Patient` (
  `patientID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `admissionDate` date DEFAULT NULL,
  `admission_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`patientID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Patient`
--

LOCK TABLES `Patient` WRITE;
/*!40000 ALTER TABLE `Patient` DISABLE KEYS */;
INSERT INTO `Patient` VALUES (1,'Alice Johnson',26,'F','123 Elms St.','1234567891',NULL,'2024-01-11 00:00:00.000000'),(2,'Bob Brown',50,'M','456 Maple Ave.','9876543210',NULL,'2024-01-12 00:00:00.000000'),(3,'Charlie Davis',35,'M','789 Pine Rd.','2345678901',NULL,'2024-01-15 00:00:00.000000'),(4,'Emily Garcia',40,'F','321 Oak St.','3456789012',NULL,'2024-01-20 00:00:00.000000'),(5,'David Martinez',28,'M','654 Birch Ln.','4567890123',NULL,'2024-01-25 00:00:00.000000'),(6,'Sophia Wilson',30,'F','987 Spruce Dr.','5678901234',NULL,'2024-02-01 00:00:00.000000'),(7,'Olivia Jones',32,'F','147 Cedar St.','6789012345',NULL,'2024-02-05 00:00:00.000000'),(8,'Ethan Clark',29,'M','258 Walnut St.','7890123456',NULL,'2024-02-10 00:00:00.000000'),(9,'Mia Lopez',35,'F','369 Cherry Ln.','8901234567',NULL,'2024-02-15 00:00:00.000000'),(10,'Lucas Smith',45,'M','741 Ash Ave.','9012345678',NULL,'2024-02-20 00:00:00.000000'),(11,'Chana Chanaboon',23,'M','123 St','1231230002',NULL,'2024-12-24 00:00:00.000000');
/*!40000 ALTER TABLE `Patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
SET FOREIGN_KEY_CHECKS=1;
-- Dump completed on 2024-12-09 20:05:55
