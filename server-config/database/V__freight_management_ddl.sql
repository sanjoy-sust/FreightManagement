CREATE TABLE `freight_management`.`mail_box` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TO_EMAIL` varchar(45) NOT NULL,
  `SUBJECT` varchar(45) NOT NULL,
  `BODY_TEXT` varchar(255) NOT NULL,
  `attachment_yn` varchar(1) NOT NULL,
  `attachment_name` varchar(255),
  `status` varchar(255) NOT NULL,
  `CREATED_BY` varchar(255) NOT NULL DEFAULT 'ADMIN',
  `UPDATED_BY` varchar(255) DEFAULT NULL,
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ;