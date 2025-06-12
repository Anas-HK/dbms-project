-- Drop existing procedure first
DROP PROCEDURE IF EXISTS AddUser;

-- Create the AddUser procedure
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
END; 