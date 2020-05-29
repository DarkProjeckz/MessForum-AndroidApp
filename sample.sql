-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 08, 2020 at 05:30 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sample`
--

-- --------------------------------------------------------

--
-- Table structure for table `complaints`
--

CREATE TABLE `complaints` (
  `id` int(11) NOT NULL,
  `rollno` bigint(20) NOT NULL,
  `complaint` varchar(3000) NOT NULL,
  `statusb` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `complaints`
--

INSERT INTO `complaints` (`id`, `rollno`, `complaint`, `statusb`) VALUES
(3, 715517104014, 'PLease correct the papers', 1),
(4, 715517104014, 'Hii', 0);

-- --------------------------------------------------------

--
-- Table structure for table `fcount`
--

CREATE TABLE `fcount` (
  `id` int(11) NOT NULL,
  `breakfast` int(11) NOT NULL,
  `lunch` int(11) NOT NULL,
  `dinner` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fcount`
--

INSERT INTO `fcount` (`id`, `breakfast`, `lunch`, `dinner`) VALUES
(1, 500, 500, 500);

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `sno` int(11) NOT NULL,
  `day` varchar(10) NOT NULL,
  `food` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id`, `sno`, `day`, `food`) VALUES
(1, 1, 'Mon', 'Idli, Chutney'),
(2, 2, 'Tues', 'Dosa, Idiyappam'),
(3, 3, 'Wed', 'Ven Pongal, Vadai'),
(4, 4, 'Thurs', 'Idli, Ragi Semiya'),
(5, 5, 'Fri', 'Uthappam, Kesari'),
(6, 6, 'Sat', 'Bread Omlette'),
(7, 7, 'Sun', 'Idli, sambar'),
(8, 11, 'Mon', 'Puthina rice'),
(9, 22, 'Tues', 'Tamarind RIce'),
(10, 33, 'Wed', 'Rice, Moongdal'),
(11, 44, 'Thurs', 'Rice, Sambar'),
(12, 55, 'Fri', 'Puthina Rice, Gravy'),
(13, 66, 'Sat', 'Rice, Peas, Sambar'),
(14, 77, 'Sun', 'Veg/Non-Veg Briyani'),
(15, 111, 'Mon', 'Uthappam, Samber'),
(16, 222, 'Tues', 'Chappathi, Gravy'),
(17, 333, 'Wed', 'Roast, Veg/Non-Veg'),
(18, 444, 'Thurs', 'Chappathi, Chilli Gobi'),
(19, 555, 'Fri', 'Parotta/Roti, Gravy'),
(20, 666, 'Sat', 'Idli, Samber'),
(21, 777, 'Sun', 'Noodles');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `m_id` varchar(11) NOT NULL,
  `message` varchar(3000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `stat`
--

CREATE TABLE `stat` (
  `id` int(11) NOT NULL,
  `roll` bigint(20) NOT NULL,
  `statusb` int(11) NOT NULL,
  `date1` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stat`
--

INSERT INTO `stat` (`id`, `roll`, `statusb`, `date1`) VALUES
(12, 715517104035, 0, '2020-03-06'),
(19, 715517104013, 0, '2020-03-06'),
(20, 715517104014, 0, '2020-03-06'),
(21, 715517104011, 1, '2020-03-06'),
(23, 715517104032, 0, '2020-03-06'),
(24, 715517104032, 0, '2020-03-06'),
(25, 715517104011, 1, '2020-03-06'),
(26, 715517104019, 0, '2020-03-06'),
(27, 715517104032, 0, '2020-03-06'),
(28, 715517104011, 1, '2020-03-06'),
(29, 715517104035, 0, '2020-03-10'),
(30, 953617106013, 0, '2020-03-19'),
(31, 18, 0, '2020-03-21');

-- --------------------------------------------------------

--
-- Table structure for table `tcount`
--

CREATE TABLE `tcount` (
  `id` int(11) NOT NULL,
  `egg` int(11) NOT NULL,
  `chicken` int(11) NOT NULL,
  `mushroom` int(11) NOT NULL,
  `mutton` int(11) NOT NULL,
  `gobi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tcount`
--

INSERT INTO `tcount` (`id`, `egg`, `chicken`, `mushroom`, `mutton`, `gobi`) VALUES
(1, 51, 45, 29, 31, 53);

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `id` int(11) NOT NULL,
  `regno` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `tname` varchar(20) NOT NULL,
  `tnum` varchar(20) NOT NULL,
  `toknum` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `token`
--

INSERT INTO `token` (`id`, `regno`, `name`, `tname`, `tnum`, `toknum`) VALUES
(274, 715517104014, 'Ashif', 'egg', '1', '0503200149'),
(275, 715517104014, 'Ashif', 'chicken', '1', '0503200242'),
(276, 715517104014, 'Ashif', 'mutton', '1', '0503200329'),
(277, 715517104014, 'Ashif', 'mushroom', '1', '0503200425'),
(278, 715517104014, 'Ashif', 'gobi', '1', '0503200551'),
(279, 715517104011, 'Arun', 'chicken', '1', '2003050243'),
(280, 715517104011, 'Arun', 'mushroom', '3', '2003060552');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `rollno` bigint(20) NOT NULL,
  `pass` varchar(128) NOT NULL,
  `passkey` varchar(16) NOT NULL,
  `name` varchar(20) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `batch` varchar(10) NOT NULL,
  `dept` varchar(10) NOT NULL,
  `room` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `rollno`, `pass`, `passkey`, `name`, `gender`, `batch`, `dept`, `room`) VALUES
(2, 715517104014, '5f7b505bdd8559f11bd144e3421108174e32aef2f1cff7ed10a30c3d2dd009c726e7de03aff6e200f40c11fcc30bf832d9efbc795510a0101f1c944b7f81e036', '61b096e9bab4c7f8', 'Ashif', 'Male', '2017-2021', 'CSE', 'A-404'),
(12, 715517104019, '5f9911a0fb7d92f7802c3b6387a6acfeab1f33adf206f13700eb0f14c3d386b0ec795f61787b3369bc9ef894464a028c5363ced9cff78f719faf9aa693fc9620', '899ae215d89ca593', 'Dhanesh', 'Male', '2017-2021', 'CSE', 'A-403'),
(14, 715517104011, '6c73f42d874ffa1655e8d26b73e6f1232b28d318016d83e028430631d6bf61110a1c34b5dd3a27ad4cb8fa5a170bc591bade6d09b0599147cfd7008a3b62d29b', 'ae6529c562212ede', 'Arun', 'Male', '2017-2021', 'Cse', 'A-410'),
(15, 715517104035, '1f4b8bcc9336b139ba30fbbc83825d3613ee8e6920ee71d4a8ca304a7ee62464b11005d294e8db7a671f0795be87a72313c707d4f4194519b958bb3f99fe6a6b', '7814b586514e27bf', 'Krishna', 'Male', '2017-2021', 'CSE', 'A-404'),
(16, 953617106013, '22d484b60ab48532ba81550ebe930d498c07a2bd0eb2f5d9b51d2083af1035b828ec31320ea3f31e6f16fda0314286c73e502c6b6bdbda3b7ce810b0ec947293', 'da4a6df0864652e5', 'Arunan', 'Male', '2017-2021', 'ECE', 'B-12');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `complaints`
--
ALTER TABLE `complaints`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fcount`
--
ALTER TABLE `fcount`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stat`
--
ALTER TABLE `stat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tcount`
--
ALTER TABLE `tcount`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `complaints`
--
ALTER TABLE `complaints`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `fcount`
--
ALTER TABLE `fcount`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `stat`
--
ALTER TABLE `stat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `tcount`
--
ALTER TABLE `tcount`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `token`
--
ALTER TABLE `token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=285;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
