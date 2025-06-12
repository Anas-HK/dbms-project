-- Add these stored procedures to your cinema database

-- Drop existing procedures first to avoid conflicts
DROP PROCEDURE IF EXISTS AddUser;
DROP PROCEDURE IF EXISTS UpdateUserPassword;
DROP PROCEDURE IF EXISTS UpdateUserEmail;

-- Stored procedure to add a new user
DELIMITER //
CREATE PROCEDURE AddUser(
    IN p_user_id INT,
    IN p_first_name VARCHAR(50),
    IN p_last_name VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_phone VARCHAR(20),
    IN p_password VARCHAR(255)
)
BEGIN
    INSERT INTO users (user_id, username, password, first_name, last_name, email, phone)
    VALUES (p_user_id, CONCAT(p_first_name, '.', p_last_name), p_password, p_first_name, p_last_name, p_email, p_phone);
END //
DELIMITER ;

-- Stored procedure to update user password
DELIMITER //
CREATE PROCEDURE UpdateUserPassword(
    IN p_user_id INT,
    IN p_new_password VARCHAR(255)
)
BEGIN
    UPDATE users
    SET password = p_new_password
    WHERE user_id = p_user_id;
END //
DELIMITER ;

-- Stored procedure to update user email
DELIMITER //
CREATE PROCEDURE UpdateUserEmail(
    IN p_user_id INT,
    IN p_new_email VARCHAR(100)
)
BEGIN
    UPDATE users
    SET email = p_new_email
    WHERE user_id = p_user_id;
END //
DELIMITER ;

-- You can add more stored procedures here as needed 