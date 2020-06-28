package Model;

import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CountryData {

    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ArrayList<Integer> indexList = new ArrayList<Integer>();

    public static void setAllCountries() {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM country;");

            while (set.next()) {
                Country newCountry = new Country(set.getInt(1), set.getString(2));
                allCountries.add(newCountry);
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    public static void resetAllCountries() {
        allCountries.removeAll(allCountries);
        setAllCountries();
    }

    public static void setSelectedCountryId(int index) {
        indexList.add(index);
    }

    public static int getSelectedCountryId() {
        return indexList.get(0);
    }

    public static void resetIndex() {
        indexList.removeAll(indexList);
    }

    public static void resetAll() {
        CountryData.resetAllCountries();
        CountryData.resetIndex();
    }

}
