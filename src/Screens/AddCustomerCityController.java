/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.City;
import Model.CityData;
import static Model.CityData.getSelectedCityId;
import Model.CountryData;
import static Model.CountryData.getSelectedCountryId;
import Model.CustomerRecordsData;
import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import static Model.GetMax.getMaxCityId;
import Model.UserData;
import java.io.IOException;
import java.net.URL;
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
public class AddCustomerCityController implements Initializable {

    @FXML
    private TextField cityTextField;
    @FXML
    private TableView<City> cityTableView;
    @FXML
    private TableColumn<City, String> cityColumn;

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

            CityData.resetAll();
            CountryData.resetAll();
            CustomerRecordsData.resetAll();
        }
    }

    public void addCityNextButtonPushed(ActionEvent event) throws IOException {
        try {
            int index = cityTableView.getSelectionModel().getSelectedItem().getCityId();

            CityData.setSelectedCityId(index);
            System.out.println("City index is " + getSelectedCityId());

            Parent parent = FXMLLoader.load(getClass().getResource("/Screens/AddCustomerAddress.fxml"));
            Scene scene = new Scene(parent);

            Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

            screen.setScene(scene);
            screen.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select City Error");
            alert.setContentText("Must select a city!");

            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                alert.close();
            }
        } catch (RuntimeException e) {
        }
    }

    public void addNewCityButtonPushed(ActionEvent event) throws IOException {
        if (cityTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add New City Error");
            alert.setContentText("Must include city name!");

            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                alert.close();
            }

        } else {
            try {
                Class.forName(mySQLjdbcDriver);
                String query = ("INSERT INTO city"
                        + "(cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES(?,?,?,NOW(),?,NOW(),?)");
                PreparedStatement pStatement = conn.prepareStatement(query);
                pStatement.setInt(1, getMaxCityId());
                pStatement.setString(2, cityTextField.getText());
                pStatement.setInt(3, getSelectedCountryId());
                pStatement.setString(4, UserData.getCurrentUser());
                pStatement.setString(5, UserData.getCurrentUser());

                pStatement.executeUpdate();

                CityData.resetCitiesInCountry();

            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void initializeData(City city) {
        cityTableView.getSelectionModel().select(CityData.getSelectedIndex() - 1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityTableView.setItems(CityData.getCitiesInCountry());
    }
}
