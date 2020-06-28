/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.CityData;
import Model.Country;
import Model.CountryData;
import Model.CustomerRecords;
import Model.CustomerRecordsData;
import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.jdbcURL;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import static Model.DatabaseConnection.password;
import static Model.DatabaseConnection.userName;
import static Model.GetMax.getMaxCountryId;
import Model.UserData;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class AddCustomerCountryController implements Initializable {

    @FXML
    private TextField countryTextField;
    @FXML
    private TableView<Country> countryTableView;
    @FXML
    private TableColumn<Country, String> countryColumn;
    @FXML
    private CustomerRecords selectedCustomer;

    public void addCustomerCancelButtonPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Customer Confirmation");
        alert.setContentText("Are you sure you want to cancel adding this customer?");

        Optional<ButtonType> results = alert.showAndWait();

        if (results.get() == ButtonType.OK) {

            Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerRecordsScreen.fxml"));
            Scene scene = new Scene(parent);

            Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

            screen.setScene(scene);
            screen.show();

            try {
                CityData.resetAll();
                CountryData.resetAll();
                CustomerRecordsData.resetAll();
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }

    public void addCustomerNextButtonPushed(ActionEvent event) throws IOException {
        try {
            int index = countryTableView.getSelectionModel().getSelectedItem().getCountryId();

            CountryData.setSelectedCountryId(index);
            System.out.println("Country index is " + CountryData.getSelectedCountryId());

            CityData.setCitiesInCountry();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Screens/AddCustomerCity.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            AddCustomerCityController controller = loader.getController();

            Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

            screen.setScene(scene);
            screen.show();

            if (CustomerRecordsData.getUpdateBoolean() == false) {
                CityData.setSelectedCity();
                CityData.getSelectedIndex();
                controller.initializeData(CityData.getSelectedCity().get(0));
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select Country Error");
            alert.setContentText("Must select a country!");

            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                alert.close();
            }
        } catch (RuntimeException e) {
        }
    }

    public void addNewCountryButtonPushed(ActionEvent event) throws IOException {
        if (countryTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add New Country Error");
            alert.setContentText("Must include country name!");

            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                alert.close();
            }

        } else {
            try {

                Class.forName(mySQLjdbcDriver);
                conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
                String query = ("INSERT INTO country"
                        + "(countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES(?,?,NOW(),?,NOW(),?)");
                PreparedStatement pStatement = conn.prepareStatement(query);
                pStatement.setInt(1, getMaxCountryId());
                pStatement.setString(2, countryTextField.getText());
                pStatement.setString(3, UserData.getCurrentUser());
                pStatement.setString(4, UserData.getCurrentUser());

                pStatement.executeUpdate();
                CountryData.resetAllCountries();

            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void initializeData(CustomerRecords customer) {
        selectedCustomer = customer;
        selectedCustomer.getAddressId();
        CustomerRecordsData.setCustomersCity(selectedCustomer);
        CustomerRecordsData.setCustomersCountry(selectedCustomer);
        countryTableView.getSelectionModel().select(CustomerRecordsData.getCustomersCountry() - 1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryTableView.setItems(CountryData.getAllCountries());
    }

}
