package Model;

import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressData {

    private static ObservableList<Address> allAddresses = FXCollections.observableArrayList();
    private static ArrayList<Integer> indexList = new ArrayList<Integer>();

    public static void setAllAddresses() {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM address;");

            while (set.next()) {
                Address newAddress = new Address(set.getInt(1), set.getString(2),
                        set.getString(3), set.getInt(5), set.getString(6));

                allAddresses.add(newAddress);

            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ObservableList<Address> getAllAddresses() {
        return allAddresses;
    }

    public static void resetAllAddresses() {
        allAddresses.removeAll(allAddresses);
        setAllAddresses();
    }

    public static void setSelectedAddressId(int index) {
        indexList.add(index);
    }

    public static int getSelectedAddressId() {
        return indexList.get(0);
    }

    public static void resetSelectedAddressId() {
        indexList.removeAll(indexList);
    }

    public static void resetAll() {
        AddressData.resetAllAddresses();
        AddressData.resetSelectedAddressId();
    }

}
