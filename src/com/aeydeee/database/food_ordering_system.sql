-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 14, 2023 at 10:38 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `food_ordering_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `menus`
--

CREATE TABLE `menus` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `category` varchar(200) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menus`
--

INSERT INTO `menus` (`id`, `name`, `category`, `quantity`, `price`, `image`) VALUES
(10, 'Beer Can Chicken', 'Menu', 69, 240, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\beer_can_chicken.jpg'),
(11, 'Spatchcock Chicken', 'Menu', 99, 130, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Spatchcock Chicken.jpg'),
(12, 'Butterflied Chicken', 'Menu', 69, 210, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Butterflied Chicken.jpg'),
(13, 'Lumpia Chicken', 'Menu', 69, 85, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Lumpia Chicken.jpg'),
(14, 'Chicken Kabobs', 'Menu', 85, 250, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Chicken Kabobs.jpg'),
(15, 'Chicken Adobo', 'Menu', 59, 76, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Chicken Adobo.jpg'),
(16, 'Malunggay Milk tea', 'Drinks', 45, 89, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Malunggay.jpg'),
(17, 'Cobra E-Drink', 'Drinks', 999, 25, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Cobra.jpg'),
(18, 'Sting E-Drink', 'Drinks', 999, 25, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Mineral.jpg'),
(19, 'Mineral Water', 'Drinks', 696, 15, 'C:\\Users\\aeyde\\Downloads\\menuAndDrinks\\Lumpia Chicken.jpg'),
(20, 'Redhorse', 'Drinks', 555, 120, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Redhorse.jpg'),
(21, 'GIN Round', 'Drinks', 64, 75, 'C:\\Users\\aeyde\\OneDrive\\Documents\\NetBeansProjects\\OnlineFoodOrderingSystem\\src\\menuAndDrinksPictures\\Gin.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `date_ordered` date DEFAULT NULL,
  `order_status` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `user_id`, `item_name`, `quantity`, `total_price`, `date_ordered`, `order_status`) VALUES
(1, 35, 'Beer Can Chicken', 1, 240.00, NULL, NULL),
(2, 35, 'Spatchcock Chicken', 1, 130.00, NULL, NULL),
(3, 35, 'Butterflied Chicken', 1, 210.00, NULL, NULL),
(4, 35, 'Lumpia Chicken', 1, 85.00, NULL, 0),
(5, 35, 'Chicken Kabobs', 1, 250.00, NULL, NULL),
(6, 35, 'Chicken Adobo', 1, 76.00, NULL, 0),
(7, 35, 'Malunggay Milk tea', 1, 89.00, NULL, NULL),
(8, 35, 'Cobra E-Drink', 1, 25.00, NULL, NULL),
(9, 35, 'Sting E-Drink', 1, 25.00, NULL, 1),
(10, 35, 'Mineral Water', 1, 15.00, NULL, 1),
(11, 35, 'Redhorse', 1, 120.00, NULL, 1),
(12, 35, 'GIN Round', 1, 75.00, NULL, 0),
(13, 41, 'Beer Can Chicken', 1, 240.00, NULL, NULL),
(14, 41, 'Malunggay Milk tea', 1, 89.00, NULL, NULL),
(15, 35, 'Chicken Adobo', 1, 76.00, '2023-11-14', 0),
(16, 35, 'GIN Round', 1, 75.00, '2023-11-14', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_fname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_lname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `user_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_birthdate` date NOT NULL,
  `user_house_no` varchar(11) NOT NULL,
  `user_barangay` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_zip` varchar(4) NOT NULL,
  `image_path` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_fname`, `user_lname`, `category`, `user_username`, `user_email`, `user_password`, `user_birthdate`, `user_house_no`, `user_barangay`, `user_city`, `user_zip`, `image_path`) VALUES
(34, 'Adrian Samuel', 'Olleres', 'admin', 'aeydeee', 'aeydeee@gmail.com', 'd5dd94bfcb40c3eb4f5fbfa1afa591e6cd7f6c66df9974fc451fe775e3127ca4', '2002-04-08', '023', 'Topas Proper', 'Nabua', '4434', 'C:\\Users\\aeyde\\OneDrive\\Pictures\\ID Pics\\2x2.jpg'),
(35, 'Janice Garcia', 'Bacsain', 'customer', 'janisayy', 'janisayy@gmail.com', 'd5dd94bfcb40c3eb4f5fbfa1afa591e6cd7f6c66df9974fc451fe775e3127ca4', '2001-03-12', '111', 'San Nicolas', 'Baao', '4432', 'C:\\Users\\aeyde\\Downloads\\1x1.5(7) 2 copies.jpg'),
(36, 'Adrian Samuel', 'Olleres', 'customer', 'kamauits', 'aeydeee@gmail.com', '90cb81b0ee7b84921076e726a37a9c597730a667677fe1e31221a27e9d27606e', '2002-04-08', '023', 'Topas Proper', 'Nabua', '4434', 'C:\\Users\\aeyde\\OneDrive\\Pictures\\ID Pics\\2x2.jpg'),
(41, 'Lee', 'Maniscan', 'customer', 'lee', 'lee@gmail.com', 'cebf25be7b474527a6b15ca0be6f10133a6c364782c51d3f8a6192e9b8d9f714', '2023-11-09', '111', 'sdfasdf', 'asdfasdf', '111', 'C:\\Users\\aeyde\\Downloads\\outer-space-colorful-galaxy-ai-4k-wallpaper-uhdpaper.com-737@1@l.jpg'),
(42, 'John Paul', 'Alfonso', 'customer', 'Pol', 'jpdivini@gmail.com', 'a2d12f1bb27e4a7d5c5291c1ce5fb411195d44f727ce4386978d030fa49bf390', '2003-08-02', '0066', 'Paloyon Proper, Nabua, Camarines Sur', 'Nabua', '4434', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `menus`
--
ALTER TABLE `menus`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `menus`
--
ALTER TABLE `menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
