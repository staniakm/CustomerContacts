CREATE TABLE `contacts` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_CUSTOMER` int(11) NOT NULL,
  `TYPE` int(11) NOT NULL DEFAULT '0',
  `CONTACT` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

CREATE TABLE `customers` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `SURNAME` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `AGE` varchar(3) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

