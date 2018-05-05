-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u1build0.15.04.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 10, 2017 at 09:28 PM
-- Server version: 5.6.28-0ubuntu0.15.04.1
-- PHP Version: 5.6.4-4ubuntu6.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Supermarket`
--

-- --------------------------------------------------------

--
-- Table structure for table `DEPARTMENT`
--

CREATE TABLE IF NOT EXISTS `DEPARTMENT` (
  `Name` varchar(20) NOT NULL,
  `DepttID` varchar(20) NOT NULL,
  `ManagerID` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DEPARTMENT`
--

INSERT INTO `DEPARTMENT` (`Name`, `DepttID`, `ManagerID`) VALUES
('rams', '1', 'ucp1274'),
('mahis', '2', 'ucp1470'),
('deepaks', '3', 'ucp1437'),
('ssawants', '4', 'ucp1317'),
('harishh', '5', 'ucp1114'),
('Unknown', 'Unknown', 'Unknown');

-- --------------------------------------------------------

--
-- Table structure for table `EMPLOYEE`
--

CREATE TABLE IF NOT EXISTS `EMPLOYEE` (
  `Name` varchar(20) DEFAULT NULL,
  `DateOfJoining` date DEFAULT '2000-01-01',
  `EmpID` varchar(20) NOT NULL,
  `Salary` int(10) DEFAULT NULL,
  `Gender` varchar(20) DEFAULT NULL,
  `DepttID` varchar(20) DEFAULT NULL,
  `DoB` date DEFAULT NULL,
  `Contact` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `EMPLOYEE`
--

INSERT INTO `EMPLOYEE` (`Name`, `DateOfJoining`, `EmpID`, `Salary`, `Gender`, `DepttID`, `DoB`, `Contact`) VALUES
('deepworker1', '2017-11-04', 'dw1', 25000, 'Female', '3', '1990-12-12', '1234567890'),
('hworker1', '2017-11-04', 'hw1', 25000, 'Male', '5', '1990-12-12', '1234567890'),
('mahiworker1', '2017-11-04', 'mw1', 25000, 'Male', '2', '1990-12-12', '1234567890'),
('ramworker1', '2017-11-04', 'rw1', 25000, 'Male', '1', '1990-12-12', '1234567890'),
('sawpworker1', '2017-11-04', 'sw1', 25000, 'Female', '4', '1990-12-12', '1234567890'),
('test', '2017-11-09', 'test', 1200, 'Male', '1', '1212-12-12', '123456789'),
('harish', '2017-11-04', 'ucp1114', 100000, 'Male', '5', '1996-12-06', '123'),
('ramsingh', '2017-11-04', 'ucp1274', 100000, 'Male', '1', '1996-12-06', '1234'),
('sawant', '2017-11-04', 'ucp1317', 100000, 'Male', '4', '1996-12-06', '123'),
('deepak', '2017-11-04', 'ucp1437', 100000, 'Male', '3', '1996-12-06', '1234'),
('mahipal', '2017-11-04', 'ucp1470', 100000, 'Male', '2', '1996-12-06', '1234'),
(NULL, '2000-01-01', 'Unknown', NULL, NULL, 'Unknown', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `PRODUCT`
--

CREATE TABLE IF NOT EXISTS `PRODUCT` (
  `Name` varchar(20) DEFAULT NULL,
  `ProdID` varchar(20) NOT NULL,
  `DepttID` varchar(20) DEFAULT NULL,
  `Price` int(10) DEFAULT NULL,
  `Brand` varchar(20) DEFAULT NULL,
  `QuantityInStock` int(10) DEFAULT NULL,
  `SuppID` varchar(20) DEFAULT NULL,
  `Cost` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PRODUCT`
--

INSERT INTO `PRODUCT` (`Name`, `ProdID`, `DepttID`, `Price`, `Brand`, `QuantityInStock`, `SuppID`, `Cost`) VALUES
('Ceiling  Fan', 'air01', '1', 1199, 'Surya', 20, 'suppn', 1099),
('Beardo', 'beard01', '4', 1199, 'pein&pazo', 0, 'suppn', 1099),
('DBMS', 'Book01', '5', 1199, 'Arihant', 0, 'suppn', 1099),
('Code', 'code01', '2', 1199, 'Python', 90, 'suppn', 1099),
('Supporter', 'run01', '3', 1199, 'Nike', 0, 'suppn', 1099),
('Unknown', 'Unknown', 'Unknown', 0, 'Unknown', 0, 'Unknown', 0);

-- --------------------------------------------------------

--
-- Table structure for table `SALES_RECORD`
--

CREATE TABLE IF NOT EXISTS `SALES_RECORD` (
  `ProdID` varchar(20) DEFAULT NULL,
  `Date_Time` date DEFAULT NULL,
  `Units` int(10) DEFAULT NULL,
  `Price` int(10) DEFAULT NULL,
  `AmountTotal` int(10) DEFAULT NULL,
`SaleNo` int(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SALES_RECORD`
--

INSERT INTO `SALES_RECORD` (`ProdID`, `Date_Time`, `Units`, `Price`, `AmountTotal`, `SaleNo`) VALUES
('Unknown', '2017-10-26', 2, 199, 398, 1),
('Unknown', '2017-10-26', 2, 199, 398, 2),
('Unknown', '2017-10-26', 2, 199, 398, 3),
('Unknown', '2017-10-26', 2, 199, 398, 4),
('Unknown', '2017-10-26', 2, 199, 398, 5),
('Unknown', '2017-10-26', 2, 199, 398, 6),
('Unknown', '2017-10-26', 2, 199, 398, 7),
('Unknown', '2017-10-26', 2, 199, 398, 8),
('Unknown', '2017-10-26', 2, 199, 398, 9),
('Unknown', '2017-10-26', 2, 199, 398, 10),
('Unknown', '2017-10-26', 2, 199, 398, 11),
('Unknown', '2017-10-26', 2, 199, 398, 12),
('Unknown', '2017-10-26', 2, 199, 398, 13),
('Unknown', '2017-10-26', 2, 199, 398, 14),
('Unknown', '2017-10-26', 2, 199, 398, 15),
('Unknown', '2017-10-26', 2, 199, 398, 16),
('Unknown', '2017-10-26', 2, 199, 398, 17),
('Unknown', '2017-10-26', 2, 199, 398, 18),
('Unknown', '2017-10-26', 2, 199, 398, 19),
('Unknown', '2017-10-26', 2, 199, 398, 20),
('Unknown', '2017-10-26', 2, 199, 398, 21),
('Unknown', '2017-10-26', 2, 199, 398, 22),
('Unknown', '2017-10-26', 2, 169, 338, 23),
('Unknown', '2017-10-26', 2, 169, 338, 24),
('Unknown', '2017-10-26', 2, 169, 338, 25),
('Unknown', '2017-10-26', 2, 169, 338, 26),
('Unknown', '2017-10-26', 2, 169, 338, 27),
('Unknown', '2017-10-26', 2, 169, 338, 28),
('Unknown', '2017-10-26', 2, 169, 338, 29),
('Unknown', '2017-10-26', 2, 169, 338, 30),
('Unknown', '2017-10-26', 1, 199, 199, 31),
('Unknown', '2017-10-26', 1, 199, 199, 32),
('Unknown', '2017-10-26', 1, 199, 199, 33),
('Unknown', '2017-10-26', 1, 199, 199, 34),
('Unknown', '2017-10-26', 1, 199, 199, 35),
('Unknown', '2017-10-26', 1, 199, 199, 36),
('Unknown', '2017-10-26', 1, 199, 199, 37),
('Unknown', '2017-10-26', 1, 199, 199, 38),
('Unknown', '2017-10-26', 1, 199, 199, 39),
('Unknown', '2017-10-26', 1, 199, 199, 40),
('Unknown', '2017-10-26', 1, 199, 199, 41),
('Unknown', '2017-10-26', 1, 199, 199, 42),
('Unknown', '2017-10-26', 1, 199, 199, 43),
('Unknown', '2017-10-26', 1, 199, 199, 44),
('Unknown', '2017-10-26', 5, 199, 995, 45),
('Unknown', '2017-10-26', 5, 199, 995, 46),
('Unknown', '2017-10-26', 5, 199, 995, 47),
('Unknown', '2017-10-26', 5, 199, 995, 48),
('Unknown', '2017-10-26', 5, 199, 995, 49),
('Unknown', '2017-10-26', 2, 169, 338, 50),
('Unknown', '2017-10-26', 2, 169, 338, 51),
('Unknown', '2017-10-26', 2, 169, 338, 52),
('Unknown', '2017-10-26', 2, 199, 398, 53),
('Unknown', '2017-10-26', 2, 199, 398, 54),
('Unknown', '2017-10-26', 2, 199, 398, 55),
('Unknown', '2017-10-26', 2, 169, 398, 56),
('Unknown', '2017-10-26', 2, 169, 398, 57),
('Unknown', '2017-10-26', 1, 169, 169, 58),
('Unknown', '2017-10-26', 1, 169, 169, 59),
('Unknown', '2017-10-26', 1, 169, 169, 60),
('Unknown', '2017-10-26', 1, 199, 199, 61),
('Unknown', '2017-10-26', 1, 199, 199, 62),
('Unknown', '2017-10-26', 1, 199, 199, 63),
('Unknown', '2017-10-26', 1, 199, 199, 64),
('Unknown', '2017-10-26', 10, 199, 1990, 65),
('Unknown', '2017-10-26', 10, 199, 1990, 66),
('Unknown', '2017-10-26', 10, 199, 1990, 67),
('Unknown', '2017-10-26', 10, 199, 1990, 68),
('Unknown', '2017-10-26', 10, 199, 1990, 69),
('Unknown', '2017-10-26', 10, 199, 1990, 70),
('Unknown', '2017-10-26', 10, 199, 1990, 71),
('Unknown', '2017-10-26', 10, 199, 1990, 72),
('Unknown', '2017-10-26', 1, 199, 796, 73),
('Unknown', '2017-10-26', 1, 199, 796, 74),
('Unknown', '2017-10-26', 199, 199, 39601, 75),
('Unknown', '2017-10-26', 1, 199, 39601, 76),
('Unknown', '2017-10-27', 10, 199, 1990, 77),
('Unknown', '2017-10-27', 10, 199, 1990, 78),
('Unknown', '2017-10-27', 10, 199, 1990, 79),
('Unknown', '2017-10-27', 10, 169, 1690, 80),
('Unknown', '2017-10-27', 10, 169, 1690, 81),
('Unknown', '2017-10-27', 10, 169, 1690, 82),
('Unknown', '2017-10-27', 10, 169, 1690, 83),
('Unknown', '2017-10-27', 10, 169, 1690, 84),
('Unknown', '2017-10-27', 10, 199, 1990, 85),
('Unknown', '2017-10-27', 10, 199, 1990, 86),
(NULL, '2017-10-29', 100, 111, 11100, 87),
(NULL, '2017-10-29', 100, 111, 11100, 88),
(NULL, '2017-10-29', 100, 111, 11100, 89),
('code01', '2017-11-09', 10, 1199, 11990, 90);

-- --------------------------------------------------------

--
-- Table structure for table `SUPPLIER`
--

CREATE TABLE IF NOT EXISTS `SUPPLIER` (
  `Name` varchar(20) DEFAULT NULL,
  `SuppID` varchar(20) NOT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  `ContactInfo` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SUPPLIER`
--

INSERT INTO `SUPPLIER` (`Name`, `SuppID`, `Gender`, `ContactInfo`) VALUES
('Anil', 'suppan', 'Male', '+91-9899891212'),
('nitishh', 'suppn', 'Male', '12345678'),
(NULL, 'Unknown', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `USERS`
--

CREATE TABLE IF NOT EXISTS `USERS` (
  `UserID` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Type` varchar(10) DEFAULT 'EMPLOYEE'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `USERS`
--

INSERT INTO `USERS` (`UserID`, `Password`, `Type`) VALUES
('1', '1', 'Admin'),
('2015ucp1274', '4721', 'Admin'),
('2015ucp1470', '1470', 'Employee'),
('ucp1114', '1114', 'Employee'),
('ucp1274', '1274', 'Employee'),
('ucp1317', '1317', 'Employee'),
('ucp1437', '1437', 'Employee'),
('ucp1470', '1470', 'Employee');

-- --------------------------------------------------------

--
-- Table structure for table `_ORDER`
--

CREATE TABLE IF NOT EXISTS `_ORDER` (
`OrderID` int(10) NOT NULL,
  `ProdID` varchar(20) DEFAULT NULL,
  `Quantity` int(10) DEFAULT NULL,
  `Cost` int(10) DEFAULT NULL,
  `OrderDate` date DEFAULT NULL,
  `AmountTotal` int(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `_ORDER`
--

INSERT INTO `_ORDER` (`OrderID`, `ProdID`, `Quantity`, `Cost`, `OrderDate`, `AmountTotal`) VALUES
(1, 'Unknown', 100, 159, '2017-10-26', 15900),
(2, 'Unknown', 100, 159, '2017-10-26', 15900),
(3, 'Unknown', 100, 149, '2017-10-26', 14900),
(4, 'Unknown', 100, 149, '2017-10-26', 14900),
(5, 'Unknown', 200, 159, '2017-10-27', 31800),
(6, 'Unknown', 200, 159, '2017-10-27', 31800),
(7, NULL, 100, 81, '2017-10-29', 8100),
(8, NULL, 100, 81, '2017-10-29', 8100),
(9, NULL, 100, 81, '2017-10-29', 8100),
(10, 'air01', 10, 1099, '2017-11-07', 10990),
(11, 'air01', 10, 1099, '2017-11-09', 10990),
(12, 'code01', 100, 1099, '2017-11-09', 109900);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `DEPARTMENT`
--
ALTER TABLE `DEPARTMENT`
 ADD PRIMARY KEY (`DepttID`), ADD KEY `DEPARTMENT_ibfk_1` (`ManagerID`);

--
-- Indexes for table `EMPLOYEE`
--
ALTER TABLE `EMPLOYEE`
 ADD PRIMARY KEY (`EmpID`), ADD KEY `DepttID` (`DepttID`);

--
-- Indexes for table `PRODUCT`
--
ALTER TABLE `PRODUCT`
 ADD PRIMARY KEY (`ProdID`), ADD KEY `PRODUCT_ibfk_1` (`DepttID`), ADD KEY `PRODUCT_ibfk_2` (`SuppID`);

--
-- Indexes for table `SALES_RECORD`
--
ALTER TABLE `SALES_RECORD`
 ADD PRIMARY KEY (`SaleNo`), ADD KEY `SALES_RECORD_ibfk_1` (`ProdID`);

--
-- Indexes for table `SUPPLIER`
--
ALTER TABLE `SUPPLIER`
 ADD PRIMARY KEY (`SuppID`);

--
-- Indexes for table `USERS`
--
ALTER TABLE `USERS`
 ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `_ORDER`
--
ALTER TABLE `_ORDER`
 ADD PRIMARY KEY (`OrderID`), ADD KEY `_ORDER_ibfk_1` (`ProdID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `SALES_RECORD`
--
ALTER TABLE `SALES_RECORD`
MODIFY `SaleNo` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=91;
--
-- AUTO_INCREMENT for table `_ORDER`
--
ALTER TABLE `_ORDER`
MODIFY `OrderID` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `DEPARTMENT`
--
ALTER TABLE `DEPARTMENT`
ADD CONSTRAINT `DEPARTMENT_ibfk_1` FOREIGN KEY (`ManagerID`) REFERENCES `EMPLOYEE` (`EmpID`) ON DELETE SET NULL;

--
-- Constraints for table `PRODUCT`
--
ALTER TABLE `PRODUCT`
ADD CONSTRAINT `PRODUCT_ibfk_1` FOREIGN KEY (`DepttID`) REFERENCES `DEPARTMENT` (`DepttID`) ON DELETE SET NULL,
ADD CONSTRAINT `PRODUCT_ibfk_2` FOREIGN KEY (`SuppID`) REFERENCES `SUPPLIER` (`SuppID`) ON DELETE SET NULL;

--
-- Constraints for table `SALES_RECORD`
--
ALTER TABLE `SALES_RECORD`
ADD CONSTRAINT `SALES_RECORD_ibfk_1` FOREIGN KEY (`ProdID`) REFERENCES `PRODUCT` (`ProdID`) ON DELETE SET NULL;

--
-- Constraints for table `_ORDER`
--
ALTER TABLE `_ORDER`
ADD CONSTRAINT `_ORDER_ibfk_1` FOREIGN KEY (`ProdID`) REFERENCES `PRODUCT` (`ProdID`) ON DELETE SET NULL;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
