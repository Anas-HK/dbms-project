-- Drop existing procedure first
DROP PROCEDURE IF EXISTS UpdateUserEmail;

-- Create the UpdateUserEmail procedure
CREATE PROCEDURE UpdateUserEmail(
    IN p_user_id INT,
    IN p_new_email VARCHAR(100)
)
BEGIN
    UPDATE users
    SET email = p_new_email
    WHERE user_id = p_user_id;
END; 