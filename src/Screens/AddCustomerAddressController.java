/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.AddressData;
import static Model.AddressData.getSelectedAddressId;
import static Model.AddressData.setSelectedAddressId;
import Model.CityData;
import static Model.CityData.getSelectedCityId;
import Model.CountryData;
import Model.CustomerRecords;
import Model.CustomerRecordsData;
import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.jdbcURL;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import static Model.DatabaseConnection.password;
import static Model.DatabaseConnection.userName;
import static Model.GetMax.getMaxAddressId;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class AddCustomerAddressController implements Initializable {

    @FXML
    private TextField addressTextField;
    @FXML
    private TextField address2TextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField phoneNumberTextField;
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

            CityData.resetAll();
            CountryData.resetAll();
            CustomerRecordsData.resetAll();
        }
    }

    public void addCustomerNextButtonPushed(ActionEvent event) throws IOException {
        if (addressTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add New Address Error");
            alert.setContentText("Must include address!");

            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                alert.close();
            }
        } else if (postalCodeTextField.getText().isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Add New Address Error");
            alert2.setContentText("Must include postal code!");

            Optional<ButtonType> results2 = alert2.showAndWait();
            if (results2.get() == ButtonType.OK) {
                alert2.close();
            }
        } else if (phoneNumberTextField.getText().isEmpty()) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Add New Address Error");
            alert3.setContentText("Must include phone number!");

            Optional<ButtonType> results3 = alert3.showAndWait();
            if (results3.get() == ButtonType.OK) {
                alert3.close();
            }
        } else {
            try {
                AddressData.resetSelectedAddressId();
                int index = getMaxAddressId();

                setSelectedAddressId(index);
                System.out.println("Address index is " + getSelectedAddressId());

                Class.forName(mySQLjdbcDriver);
                conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
                String query = ("INSERT INTO address"
                        + "(addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES(?,?,?,?,?,?,NOW(),?,NOW(),?)");
                PreparedStatement pStatement = conn.prepareStatement(query);
                pStatement.setInt(1, index);
                pStatement.setString(2, addressTextField.getText());
                pStatement.setString(3, address2TextField.getText());
                pStatement.setInt(4, getSelectedCityId());
                pStatement.setInt(5, Integer.parseInt(postalCodeTextField.getText()));
                pStatement.setString(6, phoneNumberTextField.getText());
                pStatement.setString(7, UserData.getCurrentUser());
                pStatement.setString(8, UserData.getCurrentUser());

                pStatement.executeUpdate();
                AddressData.resetAllAddresses();

                Parent parent = FXMLLoader.load(getClass().getResource("/Screens/AddCustomerName.fxml"));
                Scene scene = new Scene(parent);

                Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

                screen.setScene(scene);
                screen.show();

            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (CustomerRecordsData.getUpdateBoolean() == false) {
            selectedCustomer = CustomerRecordsData.getSelectedCustomer();
            addressTextField.setText(selectedCustomer.getAddress());
            address2TextField.setText(selectedCustomer.getAddress2());
            postalCodeTextField.setText(selectedCustomer.getPostalCode().toString());
            phoneNumberTextField.setText(selectedCustomer.getPhoneNumber());
        }
    }
}
