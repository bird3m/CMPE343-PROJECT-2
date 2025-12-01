-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: cmpe343_contacts
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `contact_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone_primary` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `phone_secondary` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `linkedin_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'Jon',NULL,'Snow','King in the North','05001110001',NULL,'jon.snow@got.local','https://www.linkedin.com/in/jon-snow','1971-02-02','2025-11-26 19:55:07','2025-11-26 19:55:07'),(2,'Arya',NULL,'Stark','No One','05001110002',NULL,'arya.stark@got.local','https://www.linkedin.com/in/arya-stark','1972-03-03','2025-11-26 19:55:07','2025-11-26 19:55:07'),(3,'Sansa',NULL,'Stark','Little Dove','05001110003',NULL,'sansa.stark@got.local','https://www.linkedin.com/in/sansa-stark','1973-04-04','2025-11-26 19:55:07','2025-11-26 19:55:07'),(4,'Bran',NULL,'Stark','Three-Eyed Raven','05001110004',NULL,'bran.stark@got.local','https://www.linkedin.com/in/bran-stark','1974-05-05','2025-11-26 19:55:07','2025-11-26 19:55:07'),(5,'Robb',NULL,'Stark','Young Wolf','05001110005',NULL,'robb.stark@got.local','https://www.linkedin.com/in/robb-stark','1975-06-06','2025-11-26 19:55:07','2025-11-26 19:55:07'),(6,'Eddard',NULL,'Stark','Ned','05001110006',NULL,'eddard.stark@got.local','https://www.linkedin.com/in/eddard-stark','1976-07-07','2025-11-26 19:55:07','2025-11-26 19:55:07'),(7,'Catelyn',NULL,'Stark',NULL,'05001110007',NULL,'catelyn.stark@got.local','https://www.linkedin.com/in/catelyn-stark','1977-08-08','2025-11-26 19:55:07','2025-11-26 19:55:07'),(8,'Benjen',NULL,'Stark',NULL,'05001110008',NULL,'benjen.stark@got.local','https://www.linkedin.com/in/benjen-stark','1978-09-09','2025-11-26 19:55:07','2025-11-26 19:55:07'),(9,'Daenerys',NULL,'Targaryen','Khaleesi','05001110009',NULL,'daenerys.targaryen@got.local','https://www.linkedin.com/in/daenerys','1979-10-10','2025-11-26 19:55:07','2025-11-26 19:55:07'),(10,'Viserys',NULL,'Targaryen',NULL,'05001110010',NULL,'viserys.targaryen@got.local','https://www.linkedin.com/in/viserys-targaryen','1980-11-11','2025-11-26 19:55:07','2025-11-26 19:55:07'),(11,'Rhaegar',NULL,'Targaryen',NULL,'05001110011',NULL,'rhaegar.targaryen@got.local','https://www.linkedin.com/in/rhaegar-targaryen','1981-12-12','2025-11-26 19:55:07','2025-11-26 19:55:07'),(12,'Tyrion',NULL,'Lannister','Imp','05001110012',NULL,'tyrion.lannister@got.local','https://www.linkedin.com/in/tyrion-lannister','1982-01-13','2025-11-26 19:55:07','2025-11-26 19:55:07'),(13,'Cersei',NULL,'Lannister',NULL,'05001110013',NULL,'cersei.lannister@got.local','https://www.linkedin.com/in/cersei-lannister','1983-02-14','2025-11-26 19:55:07','2025-11-26 19:55:07'),(14,'Jaime',NULL,'Lannister','Kingslayer','05001110014',NULL,'jaime.lannister@got.local','https://www.linkedin.com/in/jaime-lannister','1984-03-15','2025-11-26 19:55:07','2025-11-26 19:55:07'),(15,'Tywin',NULL,'Lannister',NULL,'05001110015',NULL,'tywin.lannister@got.local','https://www.linkedin.com/in/tywin-lannister','1985-04-16','2025-11-26 19:55:07','2025-11-26 19:55:07'),(16,'Joffrey',NULL,'Baratheon',NULL,'05001110016',NULL,'joffrey.baratheon@got.local','https://www.linkedin.com/in/joffrey-baratheon','1986-05-17','2025-11-26 19:55:07','2025-11-26 19:55:07'),(17,'Tommen',NULL,'Baratheon',NULL,'05001110017',NULL,'tommen.baratheon@got.local','https://www.linkedin.com/in/tommen-baratheon','1987-06-18','2025-11-26 19:55:07','2025-11-26 19:55:07'),(18,'Myrcella',NULL,'Baratheon',NULL,'05001110018',NULL,'myrcella.baratheon@got.local','https://www.linkedin.com/in/myrcella-baratheon','1988-07-19','2025-11-26 19:55:07','2025-11-26 19:55:07'),(19,'Sandor',NULL,'Clegane','The Hound','05001110019',NULL,'sandor.clegane@got.local','https://www.linkedin.com/in/sandor-clegane','1989-08-20','2025-11-26 19:55:07','2025-11-26 19:55:07'),(20,'Gregor',NULL,'Clegane','The Mountain','05001110020',NULL,'gregor.clegane@got.local','https://www.linkedin.com/in/gregor-clegane','1970-09-21','2025-11-26 19:55:07','2025-11-26 19:55:07'),(21,'Oberyn',NULL,'Martell','Red Viper','05001110021',NULL,'oberyn.martell@got.local','https://www.linkedin.com/in/oberyn-martell','1971-10-22','2025-11-26 19:55:07','2025-11-26 19:55:07'),(22,'Doran',NULL,'Martell',NULL,'05001110022',NULL,'doran.martell@got.local','https://www.linkedin.com/in/doran-martell','1972-11-23','2025-11-26 19:55:07','2025-11-26 19:55:07'),(23,'Ellaria',NULL,'Sand',NULL,'05001110023',NULL,'ellaria.sand@got.local','https://www.linkedin.com/in/ellaria-sand','1973-12-24','2025-11-26 19:55:07','2025-11-26 19:55:07'),(24,'Margaery',NULL,'Tyrell',NULL,'05001110024',NULL,'margaery.tyrell@got.local','https://www.linkedin.com/in/margaery-tyrell','1974-01-25','2025-11-26 19:55:07','2025-11-26 19:55:07'),(25,'Loras',NULL,'Tyrell',NULL,'05001110025',NULL,'loras.tyrell@got.local','https://www.linkedin.com/in/loras-tyrell','1975-02-26','2025-11-26 19:55:07','2025-11-26 19:55:07'),(26,'Olenna',NULL,'Tyrell','Queen of Thorns','05001110026',NULL,'olenna.tyrell@got.local','https://www.linkedin.com/in/olenna-tyrell','1976-03-27','2025-11-26 19:55:07','2025-11-26 19:55:07'),(27,'Renly',NULL,'Baratheon',NULL,'05001110027',NULL,'renly.baratheon@got.local','https://www.linkedin.com/in/renly-baratheon','1977-04-01','2025-11-26 19:55:07','2025-11-26 19:55:07'),(28,'Stannis',NULL,'Baratheon',NULL,'05001110028',NULL,'stannis.baratheon@got.local','https://www.linkedin.com/in/stannis-baratheon','1978-05-02','2025-11-26 19:55:07','2025-11-26 19:55:07'),(29,'Robert',NULL,'Baratheon',NULL,'05001110029',NULL,'robert.baratheon@got.local','https://www.linkedin.com/in/robert-baratheon','1979-06-03','2025-11-26 19:55:07','2025-11-26 19:55:07'),(30,'Brienne',NULL,'Tarth',NULL,'05001110030',NULL,'brienne.tarth@got.local','https://www.linkedin.com/in/brienne-tarth','1980-07-04','2025-11-26 19:55:07','2025-11-26 19:55:07'),(31,'Davos',NULL,'Seaworth','Onion Knight','05001110031',NULL,'davos.seaworth@got.local','https://www.linkedin.com/in/davos-seaworth','1981-08-05','2025-11-26 19:55:07','2025-11-26 19:55:07'),(32,'Melisandre',NULL,'RedWoman','Red Woman','05001110032',NULL,'melisandre@got.local','https://www.linkedin.com/in/melisandre','1982-09-06','2025-11-26 19:55:07','2025-11-26 19:55:07'),(33,'Theon',NULL,'Greyjoy',NULL,'05001110033',NULL,'theon.greyjoy@got.local','https://www.linkedin.com/in/theon-greyjoy','1983-10-07','2025-11-26 19:55:07','2025-11-26 19:55:07'),(34,'Yara',NULL,'Greyjoy',NULL,'05001110034',NULL,'yara.greyjoy@got.local','https://www.linkedin.com/in/yara-greyjoy','1984-11-08','2025-11-26 19:55:07','2025-11-26 19:55:07'),(35,'Balon',NULL,'Greyjoy',NULL,'05001110035',NULL,'balon.greyjoy@got.local','https://www.linkedin.com/in/balon-greyjoy','1985-12-09','2025-11-26 19:55:07','2025-11-26 19:55:07'),(36,'Euron',NULL,'Greyjoy',NULL,'05001110036',NULL,'euron.greyjoy@got.local','https://www.linkedin.com/in/euron-greyjoy','1986-01-10','2025-11-26 19:55:07','2025-11-26 19:55:07'),(37,'Ramsay',NULL,'Bolton',NULL,'05001110037',NULL,'ramsay.bolton@got.local','https://www.linkedin.com/in/ramsay-bolton','1987-02-11','2025-11-26 19:55:07','2025-11-26 19:55:07'),(38,'Roose',NULL,'Bolton',NULL,'05001110038',NULL,'roose.bolton@got.local','https://www.linkedin.com/in/roose-bolton','1988-03-12','2025-11-26 19:55:07','2025-11-26 19:55:07'),(39,'Hodor',NULL,'Hodor',NULL,'05001110039',NULL,'hodor@got.local','https://www.linkedin.com/in/hodor','1989-04-13','2025-11-26 19:55:07','2025-11-26 19:55:07'),(40,'Gendry',NULL,'Baratheon',NULL,'05001110040',NULL,'gendry@got.local','https://www.linkedin.com/in/gendry','1970-05-14','2025-11-26 19:55:07','2025-11-26 19:55:07'),(41,'Missandei',NULL,'OfNaath',NULL,'05001110041',NULL,'missandei@got.local','https://www.linkedin.com/in/missandei','1971-06-15','2025-11-26 19:55:07','2025-11-26 19:55:07'),(42,'Grey',NULL,'Worm',NULL,'05001110042',NULL,'grey.worm@got.local','https://www.linkedin.com/in/grey-worm','1972-07-16','2025-11-26 19:55:07','2025-11-26 19:55:07'),(43,'Jorah',NULL,'Mormont',NULL,'05001110043',NULL,'jorah.mormont@got.local','https://www.linkedin.com/in/jorah-mormont','1973-08-17','2025-11-26 19:55:07','2025-11-26 19:55:07'),(44,'Khal',NULL,'Drogo',NULL,'05001110044',NULL,'khal.drogo@got.local','https://www.linkedin.com/in/khal-drogo','1974-09-18','2025-11-26 19:55:07','2025-11-26 19:55:07'),(45,'Tormund',NULL,'Giantsbane',NULL,'05001110045',NULL,'tormund.giantsbane@got.local','https://www.linkedin.com/in/tormund-giantsbane','1975-10-19','2025-11-26 19:55:07','2025-11-26 19:55:07'),(46,'Jaqen',NULL,'Hghar','Faceless Man','05001110046',NULL,'jaqen.hghar@got.local','https://www.linkedin.com/in/jaqen-hghar','1976-11-20','2025-11-26 19:55:07','2025-11-26 19:55:07'),(47,'Podrick',NULL,'Payne',NULL,'05001110047',NULL,'podrick.payne@got.local','https://www.linkedin.com/in/podrick-payne','1977-12-21','2025-11-26 19:55:07','2025-11-26 19:55:07'),(48,'Petyr',NULL,'Baelish','Littlefinger','05001110048',NULL,'petyr.baelish@got.local','https://www.linkedin.com/in/petyr-baelish','1978-01-22','2025-11-26 19:55:07','2025-11-26 19:55:07'),(49,'Lord',NULL,'Varys','Spider','05001110049',NULL,'varys@got.local','https://www.linkedin.com/in/varys','1979-02-23','2025-11-26 19:55:07','2025-11-26 19:55:07'),(50,'Bronn',NULL,'Blackwater',NULL,'05001110050',NULL,'bronn@got.local','https://www.linkedin.com/in/bronn','1980-03-24','2025-11-26 19:55:07','2025-11-26 19:55:07');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-26 23:18:13
