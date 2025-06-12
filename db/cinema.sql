-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 12, 2025 at 01:35 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cinema`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int NOT NULL,
  `category_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `category_name`) VALUES
(1, 'Electronics'),
(2, 'Clothing'),
(3, 'Books'),
(4, 'Home & Kitchen'),
(5, 'Sports & Outdoors'),
(6, 'Beauty & Personal Care'),
(7, 'Toys & Games'),
(8, 'Automotive');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int NOT NULL,
  `user_id` int NOT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` decimal(10,2) NOT NULL,
  `status` enum('pending','processing','shipped','delivered','cancelled') DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `order_date`, `total_amount`, `status`) VALUES
(1, 2, '2025-05-24 23:30:03', '749.98', 'delivered'),
(2, 3, '2025-05-29 23:30:03', '1299.99', 'shipped'),
(3, 4, '2025-06-01 23:30:03', '214.97', 'processing'),
(4, 2, '2025-06-02 23:30:03', '89.99', 'pending'),
(5, 5, '2025-06-03 23:30:03', '179.98', 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `order_item_id` int NOT NULL,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`order_item_id`, `order_id`, `product_id`, `quantity`, `price`) VALUES
(1, 1, 1, 1, '699.99'),
(2, 1, 5, 2, '19.99'),
(3, 2, 2, 1, '1299.99'),
(4, 3, 7, 1, '39.99'),
(5, 3, 9, 1, '89.99'),
(6, 3, 11, 2, '29.99'),
(7, 4, 9, 1, '89.99'),
(8, 5, 6, 1, '49.99'),
(9, 5, 8, 1, '24.99'),
(10, 5, 12, 3, '24.99');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `payment_id` int NOT NULL,
  `order_id` int NOT NULL,
  `payment_method` enum('credit_card','debit_card','paypal','bank_transfer') NOT NULL,
  `payment_status` enum('pending','completed','failed','refunded') DEFAULT 'pending',
  `amount` decimal(10,2) NOT NULL,
  `payment_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`payment_id`, `order_id`, `payment_method`, `payment_status`, `amount`, `payment_date`) VALUES
(1, 1, 'credit_card', 'completed', '749.98', '2025-05-24 23:30:03'),
(2, 2, 'paypal', 'completed', '1299.99', '2025-05-29 23:30:03'),
(3, 3, 'debit_card', 'completed', '214.97', '2025-06-01 23:30:03'),
(4, 4, 'credit_card', 'pending', '89.99', '2025-06-02 23:30:03'),
(5, 5, 'paypal', 'pending', '179.98', '2025-06-03 23:30:03');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `description` text,
  `price` decimal(10,2) NOT NULL,
  `stock_quantity` int NOT NULL DEFAULT '0',
  `category_id` int DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `product_name`, `description`, `price`, `stock_quantity`, `category_id`, `image_url`, `created_at`) VALUES
(1, 'Smartphone X', 'Latest smartphone with advanced features', '699.99', 50, 1, 'smartphone.jpg', '2025-06-03 23:30:03'),
(2, 'Laptop Pro', 'High-performance laptop for professionals', '1299.99', 25, 1, 'laptop.jpg', '2025-06-03 23:30:03'),
(3, 'Wireless Earbuds', 'True wireless earbuds with noise cancellation', '149.99', 100, 1, 'earbuds.jpg', '2025-06-03 23:30:03'),
(4, 'Smart Watch', 'Fitness tracker and smartwatch with heart rate monitor', '199.99', 30, 1, 'smartwatch.jpg', '2025-06-03 23:30:03'),
(5, 'Men\'s T-shirt', 'Cotton t-shirt for men', '19.99', 200, 2, 'tshirt.jpg', '2025-06-03 23:30:03'),
(6, 'Women\'s Jeans', 'High-waisted jeans for women', '49.99', 150, 2, 'jeans.jpg', '2025-06-03 23:30:03'),
(7, 'Programming in Java', 'Learn Java programming from scratch', '39.99', 75, 3, 'java_book.jpg', '2025-06-03 23:30:03'),
(8, 'Novel: The Mystery', 'Bestselling mystery novel', '24.99', 100, 3, 'novel.jpg', '2025-06-03 23:30:03'),
(9, 'Coffee Maker', 'Automatic coffee maker for home use', '89.99', 30, 4, 'coffee_maker.jpg', '2025-06-03 23:30:03'),
(10, 'Kitchen Knife Set', 'Professional chef knife set', '129.99', 20, 4, 'knife_set.jpg', '2025-06-03 23:30:03'),
(11, 'Basketball', 'Official size basketball', '29.99', 50, 5, 'basketball.jpg', '2025-06-03 23:30:03'),
(12, 'Yoga Mat', 'Non-slip yoga mat with carrying strap', '24.99', 80, 5, 'yoga_mat.jpg', '2025-06-03 23:30:03');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `review_id` int NOT NULL,
  `product_id` int NOT NULL,
  `user_id` int NOT NULL,
  `rating` int NOT NULL,
  `comment` text,
  `review_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`review_id`, `product_id`, `user_id`, `rating`, `comment`, `review_date`) VALUES
(1, 1, 2, 5, 'Great smartphone! The camera quality is excellent.', '2025-05-26 23:30:03'),
(2, 1, 3, 4, 'Good phone but battery life could be better.', '2025-05-27 23:30:03'),
(3, 2, 4, 5, 'Perfect laptop for work and gaming.', '2025-05-31 23:30:03'),
(4, 5, 3, 3, 'Average quality t-shirt, but good for the price.', '2025-06-01 23:30:03'),
(5, 7, 4, 5, 'Excellent book for learning Java programming!', '2025-06-03 23:30:03'),
(6, 9, 2, 4, 'Makes great coffee, but a bit noisy.', '2025-05-28 23:30:03');

-- --------------------------------------------------------

--
-- Table structure for table `shipping`
--

CREATE TABLE `shipping` (
  `shipping_id` int NOT NULL,
  `order_id` int NOT NULL,
  `address_line1` varchar(100) NOT NULL,
  `address_line2` varchar(100) DEFAULT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `country` varchar(50) NOT NULL,
  `shipping_method` varchar(50) NOT NULL,
  `tracking_number` varchar(50) DEFAULT NULL,
  `shipping_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `shipping`
--

INSERT INTO `shipping` (`shipping_id`, `order_id`, `address_line1`, `address_line2`, `city`, `state`, `postal_code`, `country`, `shipping_method`, `tracking_number`, `shipping_date`) VALUES
(1, 1, '123 Main St', 'Apt 4B', 'New York', 'NY', '10001', 'USA', 'Express', 'TRK123456789', '2025-05-25 23:30:03'),
(2, 2, '456 Elm St', NULL, 'Los Angeles', 'CA', '90001', 'USA', 'Standard', 'TRK987654321', '2025-05-30 23:30:03'),
(3, 3, '789 Oak Ave', 'Suite 301', 'Chicago', 'IL', '60007', 'USA', 'Express', 'TRK456789123', '2025-06-02 23:30:03'),
(4, 4, '321 Pine Rd', NULL, 'Miami', 'FL', '33101', 'USA', 'Standard', NULL, NULL),
(5, 5, '654 Maple Dr', 'Unit 7', 'Seattle', 'WA', '98101', 'USA', 'Express', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `first_name`, `last_name`, `email`, `phone`, `created_at`) VALUES
(1, 'admin', 'password', 'Admin', 'User', 'admin@example.com', '1234567890', '2025-06-03 23:30:03'),
(2, 'john.doe', 'password123', 'John', 'Doe', 'john@example.com', '9876543210', '2025-06-03 23:30:03'),
(3, 'jane.smith', 'securepass', 'Jane', 'Smith', 'jane@example.com', '5551234567', '2025-06-03 23:30:03'),
(4, 'robert.johnson', 'pass1234', 'Robert', 'Johnson', 'robert@example.com', '7778889999', '2025-06-03 23:30:03'),
(5, 'emily.davis', 'emilypass', 'Emily', 'Davis', 'emily@example.com', '4445556666', '2025-06-03 23:30:03'),
(6, 'anas.khan', 'abcd.123', 'Anas', 'Khan', 'anas@gmail.com', '03034333645', '2025-06-03 23:31:55'),
(7, 'ibrahim.khan', 'abc.123', 'Ibrahim', 'khan', 'ibrahim@gmail.com', '0303030303', '2025-06-11 10:47:29');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`order_item_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `shipping`
--
ALTER TABLE `shipping`
  ADD PRIMARY KEY (`shipping_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `order_item_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `payment_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `review_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `shipping`
--
ALTER TABLE `shipping`
  MODIFY `shipping_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `shipping`
--
ALTER TABLE `shipping`
  ADD CONSTRAINT `shipping_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
