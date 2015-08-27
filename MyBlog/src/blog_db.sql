-- MySQL dump 10.13  Distrib 5.5.20, for Win32 (x86)
--
-- Host: localhost    Database: blog_db
-- ------------------------------------------------------
-- Server version	5.5.20

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(45) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `content` text CHARACTER SET utf8,
  `user_id` int(10) unsigned NOT NULL DEFAULT '0',
  `time` varchar(50) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `type` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'哈哈','第一个帖子，心情无比畅快',10001,'1412762063129',0),(2,'滴滴','第二个帖子，心情无比畅快+1',10000,'1412762121001',0),(3,'快快快快','爱上大声地打算',10000,'1415656245442',0),(4,'assdasdasdas山东省实施','啊飒飒上升到水电费收到收到',10000,'1416598564555',0),(5,'收到滴滴','是打发斯蒂芬水电费收到等所发生的收到',10001,'1416564545454',0);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reply` (
  `_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL DEFAULT '0',
  `article_id` int(10) unsigned NOT NULL DEFAULT '0',
  `content` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `time` varchar(50) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `parent` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` VALUES (1,10001,1,'沙发沙发','124554545454',0),(2,10000,1,'板凳 哈哈哈哈哈','45456566565',1),(3,10001,2,'dfsfsdfs啦啦啦啦啦','56565656565',0),(4,10001,1,'dsdfsdfsdf','141455554544',0),(5,10001,2,'爱上看到就看见阿斯顿哭敬爱圣诞节阿克苏大家爱看圣诞节','1412904232429',3),(6,10002,2,'爱上看到就看见阿斯顿哭敬爱圣诞节阿克苏大家爱看圣诞节','1412904266643',5),(7,11,1,'asdasdasdasdasdasdasd','1415678989898',0),(8,10002,1,'eeasdeeeeasdasdasdasdasdasdasd','1415678989879',7);
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `psw` varchar(45) NOT NULL DEFAULT '',
  `photo` varchar(120) DEFAULT NULL,
  `nick` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `birth` varchar(50) DEFAULT NULL,
  `sex` varchar(2) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10013 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'abc','123456','http://ssss','hello','11111','123456','男'),(2,'???','123456','http://ssss//dsdsd','???','110110','123','?'),(3,'令狐冲','123456','http://ssss//dsdsd','小冲冲','110110','123','男'),(4,'令狐冲','1234','http://ssss//dsdsd','小冲冲','110110','123','男'),(5,'bbb','123456','http://a00:8080/MyBlog/download?filename=bbb.jpg','','',NULL,''),(6,'bb','123456','http://127.0.0.1:8080/MyBlog/download?filename=bb.jpg','','',NULL,''),(7,'bb','123456','http://127.0.0.1:8080/MyBlog/download?filename=bb.jpg','','',NULL,''),(8,'bb','123456','http://127.0.0.1:8080/MyBlog/download?filename=bb.jpg','','',NULL,''),(9,'b0b','1234560','http://127.0.0.1:8080/MyBlog/download?filename=b0b.jpg','','',NULL,''),(10,'b0b','1234560','http://127.0.0.1:8080/MyBlog/download?filename=b0b.jpg','','',NULL,''),(11,'b0b','1234560','http://127.0.0.1:8080/MyBlog/download?filename=b0b.jpg','','',NULL,''),(10000,'aaa','111','http://ssss',NULL,NULL,NULL,NULL),(10001,'abcvv','123','http://127.0.0.1:8080/sss',NULL,NULL,NULL,NULL),(10002,'abcd','111','','',NULL,NULL,NULL),(10003,'rrr','111','','',NULL,NULL,NULL),(10004,'ddd','aaa','','',NULL,NULL,NULL),(10005,'gggg','aaa','http://10.0.2.2:8080/MyBlog/download?filename=gggg.jpg','',NULL,NULL,NULL),(10006,'ffff','ddd','http://127.0.0.1:8080/MyBlog/download?filename=ffff.jpg','',NULL,NULL,NULL),(10007,'null','[Ljava.lang.String;@141fab6','','',NULL,NULL,NULL),(10008,'null','xxx','','',NULL,NULL,NULL),(10009,'mm','ccc','','',NULL,NULL,NULL),(10010,'rty','qq','http://127.0.0.1:8080/MyBlog/download?filename=rty.jpg','',NULL,NULL,NULL),(10011,'asd','222','http://127.0.0.1:8080/MyBlog/download?filename=asd.jpg','',NULL,NULL,NULL),(10012,'wqq','123','http://10.0.2.2:8080/MyBlog/download?filename=wqq.jpg','',NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-14 18:49:01
