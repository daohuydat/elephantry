CREATE DATABASE  IF NOT EXISTS `elephantrydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `elephantrydb`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: elephantrydb
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `Key` varchar(512) NOT NULL,
  `Value` varchar(512) NOT NULL,
  PRIMARY KEY (`Key`),
  UNIQUE KEY `Key_UNIQUE` (`Key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `CountryId` varchar(10) NOT NULL,
  `CountryName` varchar(100) NOT NULL,
  PRIMARY KEY (`CountryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES ('ABW','Aruba'),('AFG','Afghanistan'),('AGO','Angola'),('AIA','Anguilla'),('ALB','Albania'),('AND','Andorra'),('ANT','Netherlands Antilles'),('ARE','United Arab Emirates'),('ARG','Argentina'),('ARM','Armenia'),('ASM','American Samoa'),('ATA','Antarctica'),('ATF','French Southern territories'),('ATG','Antigua and Barbuda'),('AUS','Australia'),('AUT','Austria'),('AZE','Azerbaijan'),('BDI','Burundi'),('BEL','Belgium'),('BEN','Benin'),('BFA','Burkina Faso'),('BGD','Bangladesh'),('BGR','Bulgaria'),('BHR','Bahrain'),('BHS','Bahamas'),('BIH','Bosnia and Herzegovina'),('BLR','Belarus'),('BLZ','Belize'),('BMU','Bermuda'),('BOL','Bolivia'),('BRA','Brazil'),('BRB','Barbados'),('BRN','Brunei'),('BTN','Bhutan'),('BVT','Bouvet Island'),('BWA','Botswana'),('CAF','Central African Republic'),('CAN','Canada'),('CCK','Cocos (Keeling) Islands'),('CHE','Switzerland'),('CHL','Chile'),('CHN','China'),('CIV','CÃ´te dÂ’Ivoire'),('CMR','Cameroon'),('COD','Congo, The Democratic Republic of the'),('COG','Congo'),('COK','Cook Islands'),('COL','Colombia'),('COM','Comoros'),('CPV','Cape Verde'),('CRI','Costa Rica'),('CUB','Cuba'),('CXR','Christmas Island'),('CYM','Cayman Islands'),('CYP','Cyprus'),('CZE','Czech Republic'),('DEU','Germany'),('DJI','Djibouti'),('DMA','Dominica'),('DNK','Denmark'),('DOM','Dominican Republic'),('DZA','Algeria'),('ECU','Ecuador'),('EGY','Egypt'),('ERI','Eritrea'),('ESH','Western Sahara'),('ESP','Spain'),('EST','Estonia'),('ETH','Ethiopia'),('FIN','Finland'),('FJI','Fiji Islands'),('FLK','Falkland Islands'),('FRA','France'),('FRO','Faroe Islands'),('FSM','Micronesia, Federated States of'),('GAB','Gabon'),('GBR','United Kingdom'),('GEO','Georgia'),('GHA','Ghana'),('GIB','Gibraltar'),('GIN','Guinea'),('GLP','Guadeloupe'),('GMB','Gambia'),('GNB','Guinea-Bissau'),('GNQ','Equatorial Guinea'),('GRC','Greece'),('GRD','Grenada'),('GRL','Greenland'),('GTM','Guatemala'),('GUF','French Guiana'),('GUM','Guam'),('GUY','Guyana'),('HKG','Hong Kong'),('HMD','Heard Island and McDonald Islands'),('HND','Honduras'),('HRV','Croatia'),('HTI','Haiti'),('HUN','Hungary'),('IDN','Indonesia'),('IND','India'),('IOT','British Indian Ocean Territory'),('IRL','Ireland'),('IRN','Iran'),('IRQ','Iraq'),('ISL','Iceland'),('ISR','Israel'),('ITA','Italy'),('JAM','Jamaica'),('JOR','Jordan'),('JPN','Japan'),('KAZ','Kazakstan'),('KEN','Kenya'),('KGZ','Kyrgyzstan'),('KHM','Cambodia'),('KIR','Kiribati'),('KNA','Saint Kitts and Nevis'),('KOR','South Korea'),('KWT','Kuwait'),('LAO','Laos'),('LBN','Lebanon'),('LBR','Liberia'),('LBY','Libyan Arab Jamahiriya'),('LCA','Saint Lucia'),('LIE','Liechtenstein'),('LKA','Sri Lanka'),('LSO','Lesotho'),('LTU','Lithuania'),('LUX','Luxembourg'),('LVA','Latvia'),('MAC','Macao'),('MAR','Morocco'),('MCO','Monaco'),('MDA','Moldova'),('MDG','Madagascar'),('MDV','Maldives'),('MEX','Mexico'),('MHL','Marshall Islands'),('MKD','Macedonia'),('MLI','Mali'),('MLT','Malta'),('MMR','Myanmar'),('MNG','Mongolia'),('MNP','Northern Mariana Islands'),('MOZ','Mozambique'),('MRT','Mauritania'),('MSR','Montserrat'),('MTQ','Martinique'),('MUS','Mauritius'),('MWI','Malawi'),('MYS','Malaysia'),('MYT','Mayotte'),('NAM','Namibia'),('NCL','New Caledonia'),('NER','Niger'),('NFK','Norfolk Island'),('NGA','Nigeria'),('NIC','Nicaragua'),('NIU','Niue'),('NLD','Netherlands'),('NOR','Norway'),('NPL','Nepal'),('NRU','Nauru'),('NZL','New Zealand'),('OMN','Oman'),('PAK','Pakistan'),('PAN','Panama'),('PCN','Pitcairn'),('PER','Peru'),('PHL','Philippines'),('PLW','Palau'),('PNG','Papua New Guinea'),('POL','Poland'),('PRI','Puerto Rico'),('PRK','North Korea'),('PRT','Portugal'),('PRY','Paraguay'),('PSE','Palestine'),('PYF','French Polynesia'),('QAT','Qatar'),('REU','RÃ©union'),('ROM','Romania'),('RUS','Russian Federation'),('RWA','Rwanda'),('SAU','Saudi Arabia'),('SDN','Sudan'),('SEN','Senegal'),('SGP','Singapore'),('SGS','South Georgia and the South Sandwich Islands'),('SHN','Saint Helena'),('SJM','Svalbard and Jan Mayen'),('SLB','Solomon Islands'),('SLE','Sierra Leone'),('SLV','El Salvador'),('SMR','San Marino'),('SOM','Somalia'),('SPM','Saint Pierre and Miquelon'),('STP','Sao Tome and Principe'),('SUR','Suriname'),('SVK','Slovakia'),('SVN','Slovenia'),('SWE','Sweden'),('SWZ','Swaziland'),('SYC','Seychelles'),('SYR','Syria'),('TCA','Turks and Caicos Islands'),('TCD','Chad'),('TGO','Togo'),('THA','Thailand'),('TJK','Tajikistan'),('TKL','Tokelau'),('TKM','Turkmenistan'),('TMP','East Timor'),('TON','Tonga'),('TTO','Trinidad and Tobago'),('TUN','Tunisia'),('TUR','Turkey'),('TUV','Tuvalu'),('TWN','Taiwan'),('TZA','Tanzania'),('UGA','Uganda'),('UKR','Ukraine'),('UMI','United States Minor Outlying Islands'),('URY','Uruguay'),('USA','United States'),('UZB','Uzbekistan'),('VAT','Holy See (Vatican City State)'),('VCT','Saint Vincent and the Grenadines'),('VEN','Venezuela'),('VGB','Virgin Islands, British'),('VIR','Virgin Islands, U.S.'),('VNM','Vietnam'),('VUT','Vanuatu'),('WLF','Wallis and Futuna'),('WSM','Samoa'),('YEM','Yemen'),('YUG','Yugoslavia'),('ZAF','South Africa'),('ZMB','Zambia'),('ZWE','Zimbabwe');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `FirstName` varchar(512) NOT NULL,
  `LastName` varchar(512) NOT NULL,
  `Gender` varchar(100) NOT NULL,
  `DoB` date DEFAULT NULL,
  `Website` varchar(512) DEFAULT NULL,
  `Company` varchar(512) DEFAULT NULL,
  `Phone` varchar(100) DEFAULT NULL,
  `CustomerId` int(11) NOT NULL,
  `ProvinceId` int(11) NOT NULL,
  `PackageId` int(11) NOT NULL,
  `Address` varchar(512) DEFAULT NULL,
  `ExpiredCredit` date DEFAULT NULL,
  PRIMARY KEY (`CustomerId`),
  KEY `fk_Customer_User_idx` (`CustomerId`),
  KEY `fk_Customer_Province1_idx` (`ProvinceId`),
  KEY `fk_Customer_Package1_idx` (`PackageId`),
  CONSTRAINT `fk_Customer_Package1` FOREIGN KEY (`PackageId`) REFERENCES `package` (`PackageId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Customer_Province1` FOREIGN KEY (`ProvinceId`) REFERENCES `province` (`ProvinceId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Customer_User` FOREIGN KEY (`CustomerId`) REFERENCES `user` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('Đạt','Đào','1','1995-01-01','elephantry.com','elephantry','0978756134',20,1,1,'Chư păh',NULL);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customerpaymentinfo`
--

DROP TABLE IF EXISTS `customerpaymentinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerpaymentinfo` (
  `CustomerPaymentInfoId` int(11) NOT NULL AUTO_INCREMENT,
  `CardNumber` varchar(100) NOT NULL,
  `CVV` varchar(10) DEFAULT NULL,
  `CardExpiredDate` date DEFAULT NULL,
  `CardOwner` varchar(512) DEFAULT NULL,
  `ExpiredDate` date DEFAULT NULL,
  `CustomerId` int(11) NOT NULL,
  PRIMARY KEY (`CustomerPaymentInfoId`),
  KEY `fk_CustomerPaymentInfo_Customer1_idx` (`CustomerId`),
  CONSTRAINT `fk_CustomerPaymentInfo_Customer1` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`CustomerId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerpaymentinfo`
--

LOCK TABLES `customerpaymentinfo` WRITE;
/*!40000 ALTER TABLE `customerpaymentinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `customerpaymentinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback` (
  `FeedbackId` int(11) NOT NULL AUTO_INCREMENT,
  `Content` text NOT NULL,
  `CreatedTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `CustomerId` int(11) NOT NULL,
  PRIMARY KEY (`FeedbackId`),
  KEY `fk_Feedback_Customer_idx` (`CustomerId`),
  CONSTRAINT `fk_Feedback_Customer1` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`CustomerId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `NotiId` int(11) NOT NULL AUTO_INCREMENT,
  `Read` tinyint(4) NOT NULL DEFAULT '0',
  `Message` varchar(512) NOT NULL,
  `CreatedTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UserId` int(11) NOT NULL,
  `NotiTypeId` int(11) NOT NULL,
  PRIMARY KEY (`NotiId`),
  KEY `fk_Notification_User1_idx` (`UserId`),
  KEY `fk_Notification_NotificationType1_idx` (`NotiTypeId`),
  CONSTRAINT `fk_Notification_NotificationType1` FOREIGN KEY (`NotiTypeId`) REFERENCES `notificationtype` (`NotiTypeId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Notification_User1` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificationtype`
--

DROP TABLE IF EXISTS `notificationtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notificationtype` (
  `NotiTypeId` int(11) NOT NULL,
  `NotiName` varchar(512) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`NotiTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificationtype`
--

LOCK TABLES `notificationtype` WRITE;
/*!40000 ALTER TABLE `notificationtype` DISABLE KEYS */;
INSERT INTO `notificationtype` VALUES (1,'Start running','start running'),(2,'Finished','finished'),(3,'Failed','failed');
/*!40000 ALTER TABLE `notificationtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package` (
  `PackageId` int(11) NOT NULL,
  `PackageName` varchar(50) NOT NULL,
  `Price` double DEFAULT NULL,
  `Storage` double NOT NULL,
  `Priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`PackageId`),
  UNIQUE KEY `PackageId_UNIQUE` (`PackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
INSERT INTO `package` VALUES (1,'a',100,100,1),(2,'b',100,100,2),(3,'c',100,100,3);
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymenthistory`
--

DROP TABLE IF EXISTS `paymenthistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paymenthistory` (
  `PaymentHistoryId` int(11) NOT NULL AUTO_INCREMENT,
  `Amount` double NOT NULL,
  `CreatedTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PaymentMethodId` int(11) NOT NULL,
  `CustomerId` int(11) NOT NULL,
  PRIMARY KEY (`PaymentHistoryId`),
  KEY `fk_PaymentHistory_PaymentMethod1_idx` (`PaymentMethodId`),
  KEY `fk_PaymentHistory_Customer1_idx` (`CustomerId`),
  CONSTRAINT `fk_PaymentHistory_Customer1` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`CustomerId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_PaymentHistory_PaymentMethod1` FOREIGN KEY (`PaymentMethodId`) REFERENCES `paymentmethod` (`PaymentMethodId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymenthistory`
--

LOCK TABLES `paymenthistory` WRITE;
/*!40000 ALTER TABLE `paymenthistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymenthistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentmethod`
--

DROP TABLE IF EXISTS `paymentmethod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paymentmethod` (
  `PaymentMethodId` int(11) NOT NULL,
  `PaymentMethodName` varchar(512) NOT NULL,
  `Description` text,
  PRIMARY KEY (`PaymentMethodId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentmethod`
--

LOCK TABLES `paymentmethod` WRITE;
/*!40000 ALTER TABLE `paymentmethod` DISABLE KEYS */;
INSERT INTO `paymentmethod` VALUES (1,'Paypal','Paypal');
/*!40000 ALTER TABLE `paymentmethod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `province`
--

DROP TABLE IF EXISTS `province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `province` (
  `ProvinceId` int(11) NOT NULL AUTO_INCREMENT,
  `ProvinceName` varchar(512) NOT NULL,
  `CountryId` varchar(10) NOT NULL,
  PRIMARY KEY (`ProvinceId`),
  KEY `FK_Province_Country_idx` (`CountryId`),
  CONSTRAINT `FK_Province_Country` FOREIGN KEY (`CountryId`) REFERENCES `country` (`CountryId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province`
--

LOCK TABLES `province` WRITE;
/*!40000 ALTER TABLE `province` DISABLE KEYS */;
INSERT INTO `province` VALUES (1,'Gia Lai','VNM');
/*!40000 ALTER TABLE `province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommendation`
--

DROP TABLE IF EXISTS `recommendation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recommendation` (
  `RecommendationId` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(512) NOT NULL,
  `FolderInputURL` varchar(512) DEFAULT NULL,
  `FolderOutputURL` varchar(512) DEFAULT NULL,
  `NumOfItem` int(11) NOT NULL DEFAULT '10',
  `EstimatedDuration` double NOT NULL,
  `CustomerId` int(11) NOT NULL,
  `RecommendationStatusId` int(11) NOT NULL,
  `RecommendTypeId` int(11) NOT NULL,
  `Priority` int(11) NOT NULL,
  `CreatedTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `StartedTime` datetime DEFAULT NULL,
  `FinishedTime` datetime DEFAULT NULL,
  `Timer` datetime DEFAULT NULL,
  `Threshold` double NOT NULL DEFAULT '0',
  `FailedCount` int(11) NOT NULL DEFAULT '0',
  `RecordCount` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`RecommendationId`),
  KEY `fk_Recommendation_Customer1_idx` (`CustomerId`),
  KEY `fk_Recommendation_RecommendationStatus1_idx` (`RecommendationStatusId`),
  KEY `fk_Recommendation_RecommendType1_idx` (`RecommendTypeId`),
  CONSTRAINT `fk_Recommendation_Customer1` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`CustomerId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Recommendation_RecommendType1` FOREIGN KEY (`RecommendTypeId`) REFERENCES `recommendtype` (`RecommendTypeId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Recommendation_RecommendationStatus1` FOREIGN KEY (`RecommendationStatusId`) REFERENCES `recommendationstatus` (`RecommendationStatusId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommendation`
--

LOCK TABLES `recommendation` WRITE;
/*!40000 ALTER TABLE `recommendation` DISABLE KEYS */;
/*!40000 ALTER TABLE `recommendation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommendationstatus`
--

DROP TABLE IF EXISTS `recommendationstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recommendationstatus` (
  `RecommendationStatusId` int(11) NOT NULL,
  `RecommendationStatusName` varchar(100) NOT NULL,
  `Description` text NOT NULL,
  PRIMARY KEY (`RecommendationStatusId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommendationstatus`
--

LOCK TABLES `recommendationstatus` WRITE;
/*!40000 ALTER TABLE `recommendationstatus` DISABLE KEYS */;
INSERT INTO `recommendationstatus` VALUES (1,'Waiting','Waiting'),(2,'Submited','Submited'),(3,'Running','Running'),(4,'Completed','Completed'),(6,'Deleted','Deleted');
/*!40000 ALTER TABLE `recommendationstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommendtype`
--

DROP TABLE IF EXISTS `recommendtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recommendtype` (
  `RecommendTypeId` int(11) NOT NULL,
  `RecommendTypeName` varchar(512) NOT NULL,
  `Description` text,
  PRIMARY KEY (`RecommendTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommendtype`
--

LOCK TABLES `recommendtype` WRITE;
/*!40000 ALTER TABLE `recommendtype` DISABLE KEYS */;
INSERT INTO `recommendtype` VALUES (1,'Item Based','Item Based');
/*!40000 ALTER TABLE `recommendtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `RoleId` varchar(10) NOT NULL,
  `RoleName` varchar(50) NOT NULL,
  PRIMARY KEY (`RoleId`),
  UNIQUE KEY `RoleId_UNIQUE` (`RoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('ROLE_ADMIN','Admin'),('ROLE_CUST','Customer'),('ROLE_ROOT','Root');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translation`
--

DROP TABLE IF EXISTS `translation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `translation` (
  `TranslationKey` varchar(512) NOT NULL,
  `English` varchar(10000) NOT NULL,
  `Vietnamese` varchar(10000) NOT NULL,
  PRIMARY KEY (`TranslationKey`),
  UNIQUE KEY `TranslationKey_UNIQUE` (`TranslationKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `translation`
--

LOCK TABLES `translation` WRITE;
/*!40000 ALTER TABLE `translation` DISABLE KEYS */;
INSERT INTO `translation` VALUES ('dashboard','Dashboard','Bảng điều khiển'),('FINAL','FINAL QUEUE','HÀNG CHỜ CUỐI'),('HIGH_SUBMITTED','HIGH SUBMITTED','ƯU TIÊN CAO'),('LOW_SUBMITTED','LOW SUBMITTED','ƯU TIÊN THẤP'),('NORMAL_SUBMITTED','NORMAL SUBMITTED','ƯU TIÊN BÌNH THƯỜNG'),('PREPARED','PREPARED QUEUE','HÀNG CHỜ SẴN SÀNG'),('queueInformations','Queue informations','Thông tin hàng đợi'),('RUNNING','RUNNING','ĐANG CHẠY');
/*!40000 ALTER TABLE `translation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploadedfile`
--

DROP TABLE IF EXISTS `uploadedfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploadedfile` (
  `UploadedFileId` int(11) NOT NULL AUTO_INCREMENT,
  `FileName` text NOT NULL,
  `FileSize` double NOT NULL,
  `UploadedTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `HDFSURL` varchar(512) DEFAULT NULL,
  `DeletedTime` datetime DEFAULT NULL,
  `CustomerId` int(11) NOT NULL,
  PRIMARY KEY (`UploadedFileId`),
  KEY `fk_UploadedFile_Customer1_idx` (`CustomerId`),
  CONSTRAINT `fk_UploadedFile_Customer1` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`CustomerId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploadedfile`
--

LOCK TABLES `uploadedfile` WRITE;
/*!40000 ALTER TABLE `uploadedfile` DISABLE KEYS */;
/*!40000 ALTER TABLE `uploadedfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `UserId` int(11) NOT NULL AUTO_INCREMENT,
  `Email` text NOT NULL,
  `Password` text NOT NULL,
  `Active` tinyint(4) NOT NULL DEFAULT '1',
  `CreatedTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `RoleId` varchar(10) NOT NULL,
  PRIMARY KEY (`UserId`),
  KEY `fk_User_Role1_idx` (`RoleId`),
  CONSTRAINT `fk_User_Role1` FOREIGN KEY (`RoleId`) REFERENCES `role` (`RoleId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (17,'root@gmail.com','123',1,'2017-07-07 00:00:00','ROLE_ROOT'),(18,'ad1@gmail.com','123',1,'2017-07-07 00:00:00','ROLE_ADMIN'),(19,'ad2@gmail.com','123',1,'2017-07-07 00:00:00','ROLE_ADMIN'),(20,'datdh@gmail.com','123',1,'2017-07-07 00:00:00','ROLE_CUST');
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

-- Dump completed on 2017-07-17 15:49:21
