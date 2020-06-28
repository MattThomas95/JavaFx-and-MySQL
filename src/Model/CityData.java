package Model;

import static Model.CountryData.getSelectedCountryId;
import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CityData {

    private static ObservableList<City> allCities = FXCollections.observableArrayList();
    private static ArrayList<Integer> indexList = new ArrayList<Integer>();
    private static ObservableList<City> citiesInCountry = FXCollections.observableArrayList();
    private static ObservableList<City> selectedCity = FXCollections.observableArrayList();

    public static void setCitiesInCountry() {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM city WHERE countryId IN (SELECT countryId FROM country WHERE countryId ="
                    + getSelectedCountryId() + ");");

            while (set.next()) {
                City newCity = new City(set.getInt(1), set.getString(2));

                citiesInCountry.add(newCity);

            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void setSelectedCity() {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM city WHERE cityId ="
                    + CustomerRecordsData.getCustomersCity() + ";");

            while (set.next()) {
                City newCity = new City(set.getInt(1), set.getString(2));

                selectedCity.add(newCity);

            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ObservableList<City> getSelectedCity() {
        return selectedCity;
    }

    public static void resetSelectedCity() {
        selectedCity.removeAll(selectedCity);
    }

    public static int getSelectedIndex() {
        int newIndex = 0;

        for (City city : citiesInCountry) {
            newIndex++;
            if (city.getCityId() == CustomerRecordsData.getCustomersCity()) {
                break;
            }
        }
        return newIndex;
    }

    public static ObservableList<City> getCitiesInCountry() {
        return citiesInCountry;
    }

    public static ObservableList<City> getAllCities() {
        return allCities;
    }

    public static void resetCitiesInCountry() {
        citiesInCountry.removeAll(citiesInCountry);
        setCitiesInCountry();
    }

    public static void removeCitiesInCountry() {
        citiesInCountry.removeAll(citiesInCountry);
    }

    public static void setSelectedCityId(int index) {
        indexList.add(index);
    }

    public static int getSelectedCityId() {
        return indexList.get(0);
    }

    public static void resetAll() {
        CityData.resetCitiesInCountry();
        CityData.resetSelectedCity();
        CityData.removeCitiesInCountry();
    }

}
