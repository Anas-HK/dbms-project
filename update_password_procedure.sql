-- Drop existing procedure first
DROP PROCEDURE IF EXISTS UpdateUserPassword;

-- Create the UpdateUserPassword procedure
CREATE PROCEDURE UpdateUserPassword(
    IN p_user_id INT,
    IN p_new_password VARCHAR(255)
)
BEGIN
    UPDATE users
    SET password = p_new_password
    WHERE user_id = p_user_id;
END; 