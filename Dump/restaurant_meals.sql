-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant
-- ------------------------------------------------------
-- Server version	8.0.1-dmr-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `meals`
--

DROP TABLE IF EXISTS `meals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `id_category` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idmeals_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `category_idx` (`id_category`),
  CONSTRAINT `category` FOREIGN KEY (`id_category`) REFERENCES `menu_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meals`
--

LOCK TABLES `meals` WRITE;
/*!40000 ALTER TABLE `meals` DISABLE KEYS */;
INSERT INTO `meals` VALUES (1,'Borsch',5000,400,2),(2,'Pork grill',12000,500,3),(3,'Steak',13000,550,3),(4,'Grilled vegetables',6000,500,3),(5,'Mackerel',9000,550,3),(6,'Mushroom cream soup',7500,375,2),(7,'Cheese soup',7000,400,2),(8,'Cold kvass soup',6000,425,2),(9,'Solyanka',6000,400,2),(10,'Bolognese',8000,375,1),(11,'Italian cheese & tomato pizza',11000,500,1),(12,'Thai chicken and rise',8000,475,1),(13,'Vegetable pasta',6500,500,1),(14,'Roast chicken and potatoes',7800,450,1),(15,'Fruit salad and cream',5400,350,7),(16,'Ice cream',2500,200,7),(17,'Lemon cake',5600,230,7),(18,'Chocolate cake',4900,250,7),(19,'Bananas cake',5500,250,7),(20,'Green tea',1500,180,5),(21,'Black tea ',1500,200,5),(22,'Espreso',2000,100,5),(23,'Late',2500,300,5),(24,'Americano',2500,300,5),(25,'Cappuccino',3000,300,5),(26,'Apple juice',2000,500,4),(27,'Orange juice',2000,500,4),(28,'Cola',1500,500,4),(29,'Fanta',1500,500,4),(30,'Pepsi',1500,500,4),(31,'Beer',3000,500,6),(32,'Vine',5000,400,6),(33,'Champagne',5000,500,6),(34,'Vodka',3000,100,6),(35,'Cider',3000,500,6),(36,'Whiskey',6000,100,6);
/*!40000 ALTER TABLE `meals` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-05 16:00:41
