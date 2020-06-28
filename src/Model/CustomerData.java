package Model;

import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerData {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ArrayList<Integer> indexList = new ArrayList<Integer>();

    public static void setAllCustomers() {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM customer;");

            while (set.next()) {
                Customer newCustomer = new Customer(set.getInt(1), set.getString(2));

                allCustomers.add(newCustomer);

            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void resetAllCustomers() {
        allCustomers.removeAll(allCustomers);
        setAllCustomers();
    }

    public static void setSelectedCustomerId(int index) {
        indexList.add(index);
    }

    public static int getSelectedCustomerId() {
        return indexList.get(0);
    }

    public static void resetSelectedCustomerId() {
        indexList.removeAll(indexList);
    }

    public static int activeCustomer() {
        return 1;
    }

    public static int nonActiveCustomer() {
        return 0;
    }

    public static void resetAll() {
        CustomerData.resetAllCustomers();
        CustomerData.resetSelectedCustomerId();
    }

}
