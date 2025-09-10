/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 8.0.42 : Database - prosoft25
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`prosoft25` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `prosoft25`;

/*Table structure for table `clan` */

DROP TABLE IF EXISTS `clan`;

CREATE TABLE `clan` (
  `clanID` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prezime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `telefon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`clanID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `clan` */

insert  into `clan`(`clanID`,`ime`,`prezime`,`telefon`,`email`) values 
(1,'Pera','Peric','061123123','pera@pera.com'),
(2,'Stefan','Krunic','062123123','stefan@gmail.com'),
(3,'Aleksandar','Veliki','069135135','aca@yahoo.com'),
(4,'Petar','Petrovic','063245245','petar@gmail.com'),
(7,'test','test','3124235','af@asf'),
(9,'proba','probic','123','asf@asg'),
(10,'asfasftttt','asfasfttt','35253','asfasf@asfasf'),
(17,'test21','test21','123123123000','test2@test.com0'),
(19,'TestClan05','TestClan05','1231231230','123@123.com');

/*Table structure for table `knjiga` */

DROP TABLE IF EXISTS `knjiga`;

CREATE TABLE `knjiga` (
  `knjigaID` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `autor` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`knjigaID`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `knjiga` */

insert  into `knjiga`(`knjigaID`,`naziv`,`autor`) values 
(1,'Knjiga pet prstenova','Mijamoto Musasi'),
(2,'Umece ratovanja','Sun Tsu'),
(3,'The art of learning','Joshua Waitzkin'),
(4,'Bele noci','Dostojevski'),
(5,'Corpus Hermeticum','Hermes Trismegistus'),
(6,'Bhagavad Gita','-Nepoznat autor-'),
(7,'Kybalion','The three initiates'),
(8,'Bednici','Dostojevski'),
(9,'Selo Stepancikovo','Dostojevski'),
(10,'Kockar','Dostojevski'),
(11,'Braca Karamazovi','Dostojevski'),
(12,'Zlocin i kazna','Dostojevski'),
(13,'Gorski vijenac','Njegos'),
(14,'Luca Mikrokozma','Njegos'),
(24,'knjigaNovabla11','Vovi1bla1'),
(37,'novatest','testnova1'),
(39,'aasasas','asasasasas'),
(41,'test12','test12'),
(46,'TestKnjiga','TestAutor');

/*Table structure for table `primerak` */

DROP TABLE IF EXISTS `primerak`;

CREATE TABLE `primerak` (
  `primerakID` int NOT NULL AUTO_INCREMENT,
  `izdavac` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `godinaIzdanja` year DEFAULT NULL,
  `knjigaID` int DEFAULT NULL,
  PRIMARY KEY (`primerakID`),
  KEY `knjigaID` (`knjigaID`),
  CONSTRAINT `primerak_ibfk_1` FOREIGN KEY (`knjigaID`) REFERENCES `knjiga` (`knjigaID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `primerak` */

insert  into `primerak`(`primerakID`,`izdavac`,`godinaIzdanja`,`knjigaID`) values 
(1,'Laguna',2000,1),
(2,'IzdavacNEki',2005,1),
(3,'BlaBla1',2019,2),
(4,'neki',2020,3),
(5,'izadac',2003,4),
(6,'Praktika',2010,5),
(7,'Delphi',2014,6),
(8,'Natura',2015,7),
(9,'Metaphisica',2016,8),
(10,'Alexandria',2001,9),
(11,'Random Izdavac',2002,10),
(12,'ideja',1999,11),
(13,'nauka',1964,12),
(14,'biobio',1945,13),
(15,'planina',1977,14),
(27,'Novi1',2025,24),
(28,'Stari',2024,24),
(53,'aca',2000,37),
(54,'pera',2001,37),
(55,'joca',2002,37),
(57,'11111',2000,39),
(62,'test1',2000,41),
(63,'test22',2001,41),
(64,'test3',2002,41),
(71,'TestIzdavac',2000,46),
(72,'TestIzdavac2',2001,46);

/*Table structure for table `radnik` */

DROP TABLE IF EXISTS `radnik`;

CREATE TABLE `radnik` (
  `radnikID` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prezime` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`radnikID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `radnik` */

insert  into `radnik`(`radnikID`,`ime`,`prezime`,`username`,`password`) values 
(1,'Dejan','Colic','dejan','colic'),
(2,'Admin','Admin','admin','admin'),
(3,'Admin123','Admin123','admin123','admin123');

/*Table structure for table `stavkazaduzenja` */

DROP TABLE IF EXISTS `stavkazaduzenja`;

CREATE TABLE `stavkazaduzenja` (
  `zaduzenjeID` int NOT NULL,
  `stavkaID` int NOT NULL,
  `datumRazduzenja` date DEFAULT NULL,
  `napomena` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `primerakID` int DEFAULT NULL,
  PRIMARY KEY (`zaduzenjeID`,`stavkaID`),
  KEY `stavkaID` (`stavkaID`),
  KEY `primerakID` (`primerakID`),
  CONSTRAINT `stavkazaduzenja_ibfk_1` FOREIGN KEY (`zaduzenjeID`) REFERENCES `zaduzenje` (`zaduzenjeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stavkazaduzenja_ibfk_2` FOREIGN KEY (`primerakID`) REFERENCES `primerak` (`primerakID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `stavkazaduzenja` */

insert  into `stavkazaduzenja`(`zaduzenjeID`,`stavkaID`,`datumRazduzenja`,`napomena`,`primerakID`) values 
(1,1,NULL,'Dramatican decko, pregledati stanje knjige kad je vrati, kaze da ce da je nosi u Indiju??',3),
(2,2,'2025-07-31','Vraceno.',14),
(2,3,NULL,NULL,15),
(14,1,NULL,'1',62),
(14,2,NULL,'5',63),
(17,1,NULL,'test',71),
(18,1,'2025-09-10',NULL,2);

/*Table structure for table `zaduzenje` */

DROP TABLE IF EXISTS `zaduzenje`;

CREATE TABLE `zaduzenje` (
  `zaduzenjeID` int NOT NULL AUTO_INCREMENT,
  `datumZaduzenja` date DEFAULT NULL,
  `radnikID` int DEFAULT NULL,
  `clanID` int DEFAULT NULL,
  PRIMARY KEY (`zaduzenjeID`),
  KEY `radnikID` (`radnikID`),
  KEY `clanID` (`clanID`),
  CONSTRAINT `zaduzenje_ibfk_1` FOREIGN KEY (`radnikID`) REFERENCES `radnik` (`radnikID`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `zaduzenje_ibfk_2` FOREIGN KEY (`clanID`) REFERENCES `clan` (`clanID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `zaduzenje` */

insert  into `zaduzenje`(`zaduzenjeID`,`datumZaduzenja`,`radnikID`,`clanID`) values 
(1,'2025-07-31',1,3),
(2,'2025-07-30',2,1),
(14,'2025-09-09',2,1),
(17,'2025-09-10',1,7),
(18,'2024-12-10',1,19);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
