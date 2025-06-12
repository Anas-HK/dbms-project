/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectDB {

    static Connection connection = null;
    static String DatabaseName = "";
    static String url = "jdbc:mysql://localhost:3306/" + DatabaseName;

    static String username = "mudassir";
    static String password = "password";

    public static void main(String[] args) throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        connection = DriverManager.getConnection(url, username, password);
        PreparedStatement ps =  connection.prepareStatement("Insert into 'cinema'.'users' ('first_name') values 'Osama' ");
        int status = ps.executeUpdate();

        if(status != 0)
        {
            System.out.println("connection Successful");
            System.out.println("Data Inserted");
        }
    }
}
*/