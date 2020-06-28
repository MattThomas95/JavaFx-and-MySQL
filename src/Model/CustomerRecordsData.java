package Model;

import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerRecordsData {

    private static ObservableList<CustomerRecords> allCustomerRecords = FXCollections.observableArrayList();
    private static ObservableList<CustomerRecords> selectedCustomer = FXCollections.observableArrayList();
    private static ArrayList<Integer> customersCity = new ArrayList<Integer>();
    private static ArrayList<Integer> customersCountry = new ArrayList<Integer>();
    private static ArrayList<Integer> updateButtonClicked = new ArrayList<Integer>();
    private static ArrayList<String> customerName = new ArrayList<String>();

    public static void setAllCustomerRecords() {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM customer WHERE active = 1;");
            ResultSet set2 = statement2.executeQuery("SELECT * FROM address WHERE addressId IN (SELECT addressId FROM customer WHERE ACTIVE = 1);");

            while (set.next() & set2.next()) {
                CustomerRecords newCustomer = new CustomerRecords(set.getInt(1), set.getString(2), set2.getInt(1), set2.getString(2),
                        set2.getString(3), set2.getInt(5), set2.getString(6));
                allCustomerRecords.add(newCustomer);
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ObservableList<CustomerRecords> getAllCustomerRecords() {
        return allCustomerRecords;
    }

    public static void resetAllCustomersRecords() {
        allCustomerRecords.removeAll(allCustomerRecords);
        setAllCustomerRecords();
    }

    public static void deleteSelectedCustomer(CustomerRecords customer) {

        allCustomerRecords.remove(customer);
        try {
            Class.forName(mySQLjdbcDriver);
            String query = ("UPDATE customer SET active = 0 WHERE customerId = ?;");
            PreparedStatement pStatement = conn.prepareStatement(query);
            pStatement.setInt(1, customer.getCustomerId());
            pStatement.executeUpdate();
            resetAllCustomersRecords();

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setCustomersCity(CustomerRecords customer) {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM city WHERE cityId IN (SELECT cityId FROM address WHERE addressId ="
                    + customer.getAddressId() + ");");

            while (set.next()) {
                customersCity.add(set.getInt(1));
                System.out.println("City Id is " + CustomerRecordsData.getCustomersCity());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setCustomersCountry(CustomerRecords customer) {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM country WHERE countryId IN (SELECT countryId FROM city WHERE cityId ="
                    + getCustomersCity() + ");");

            while (set.next()) {
                customersCountry.add(set.getInt(1));
                System.out.println("Country Id is " + CustomerRecordsData.getCustomersCountry()); //need cityId 
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getCustomersCity() {
        return customersCity.get(0);
    }

    public static void resetCustomersCity() {
        customersCity.removeAll(customersCity);
    }

    public static int getCustomersCountry() {
        return customersCountry.get(0);
    }

    public static void resetCustomersCountry() {
        customersCountry.removeAll(customersCountry);
    }

    public static void updateButtonClicked() {
        updateButtonClicked.add(1);
    }

    public static int getUpdateStatus() {
        return updateButtonClicked.get(0);
    }

    public static boolean getUpdateBoolean() {
        return updateButtonClicked.isEmpty();
    }

    public static void resetUpdateStatus() {
        updateButtonClicked.removeAll(updateButtonClicked);
    }

    public static void setSelectedCustomer(CustomerRecords customer) {
        selectedCustomer.add(customer);
    }

    public static CustomerRecords getSelectedCustomer() {
        return selectedCustomer.get(0);
    }

    public static void resetSelectedCustomer() {
        selectedCustomer.removeAll(selectedCustomer);
    }

    public static int getSelectedIndex() {
        int newIndex = 0;

        for (CustomerRecords names : allCustomerRecords) {
            newIndex++;
            if (names.getCustomerId().equals(getSelectedCustomer().getCustomerId())) {
                break;
            }
        }
        return newIndex;
    }

    public static void setCustomerNameApp(String name) {
        customerName.add(name);
    }

    public static void getCustomerNameApp() {
        for (CustomerRecords names : allCustomerRecords) {
            if (names.getCustomerName().equals(customerName.get(0))) {
                setSelectedCustomer(names);
            }
        }
    }

    public static void resetCustomerNameApp() {
        customerName.removeAll(customerName);
    }

    public static void resetAll() {
        CustomerRecordsData.resetAllCustomersRecords();
        CustomerRecordsData.resetCustomerNameApp();
        CustomerRecordsData.resetCustomersCity();
        CustomerRecordsData.resetCustomersCountry();
        CustomerRecordsData.resetSelectedCustomer();
        CustomerRecordsData.resetUpdateStatus();
    }
}
