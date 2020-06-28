package Model;

import static Model.GetMax.getMaxCustomerId;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    public static final String protocol = "jdbc";
    public static final String vendorName = ":mysql:";
    public static final String ipaddress = "//3.227.166.251/U07OBI";

    public static final String jdbcURL = protocol + vendorName + ipaddress;

    public static final String mySQLjdbcDriver = "com.mysql.jdbc.Driver";
    public static Connection conn = null;

    public static final String userName = "U07OBI";
    public static final String password = "53689082650";

    public static Connection startConnection() {
        try {
            Class.forName(mySQLjdbcDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection Succesful");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void addCustomer(String name, String address, String phoneNumber) {
        try {
            Class.forName(mySQLjdbcDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
            String query = ("UPDATE customer SET customerName = ? WHERE customerId = ?"); //Take sceme out
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setInt(2, getMaxCustomerId());

            preparedStmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection Closed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
