package Model;

import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetMax {

    public static int getMaxAddressId() {
        int lastId = 0;
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT MAX(addressId) FROM address;");
            if (set.next()) {
                lastId = set.getInt(1);
            }
            lastId = lastId + 1;
            //System.out.println(lastId);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lastId;
    }

    public static int getMaxAppointmentId() {
        int lastId = 0;
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT MAX(appointmentId) FROM appointment;");
            if (set.next()) {
                lastId = set.getInt(1);
            }
            lastId = lastId + 1;
            //System.out.println(lastId);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lastId;
    }

    public static int getMaxCityId() {
        int lastId = 0;
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT MAX(cityId) FROM city;");
            if (set.next()) {
                lastId = set.getInt(1);
            }
            lastId = lastId + 1;
            //System.out.println(lastId);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lastId;
    }

    public static int getMaxCountryId() {
        int lastId = 0;
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT MAX(countryId) FROM country;");
            if (set.next()) {
                lastId = set.getInt(1);
            }
            lastId = lastId + 1;
            //System.out.println(lastId);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lastId;
    }

    public static int getMaxCustomerId() {
        int lastId = 0;
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT MAX(customerId) FROM customer;");
            if (set.next()) {
                lastId = set.getInt(1);
            }
            lastId = lastId + 1;
            //System.out.println(lastId);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lastId;
    }

    public static int getMaxUserId() {
        int lastId = 0;
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT MAX(userId) FROM user;");
            if (set.next()) {
                lastId = set.getInt(1);
            }
            lastId = lastId + 1;
            //System.out.println(lastId);

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lastId;
    }
}
