/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import static Model.AddressData.getSelectedAddressId;
import Model.CityData;
import Model.CountryData;
import static Model.CustomerData.activeCustomer;
import Model.CustomerRecords;
import Model.CustomerRecordsData;
import static Model.CustomerRecordsData.resetAllCustomersRecords;
import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import static Model.GetMax.getMaxCustomerId;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class AddCustomerNameController implements Initializable {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private String customerName;
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

    public void saveCustomerNameButtonPushed(ActionEvent event) throws IOException {
        if (CustomerRecordsData.getUpdateBoolean() == false) {
            CustomerRecordsData.deleteSelectedCustomer(CustomerRecordsData.getSelectedCustomer());
        }
        if (firstNameTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add New Customer Error");
            alert.setContentText("Must include customer's first name!");

            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                alert.close();
            }
        } else if (lastNameTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add New Customer Error");
            alert.setContentText("Must include customer's last name!");

            Optional<ButtonType> results = alert.showAndWait();
            if (results.get() == ButtonType.OK) {
                alert.close();
            }

        } else {
            try {
                customerName = firstNameTextField.getText() + " " + lastNameTextField.getText();
                Class.forName(mySQLjdbcDriver);
                String query = ("INSERT INTO customer"
                        + "(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES(?,?,?,?,NOW(),?,NOW(),?)");
                PreparedStatement pStatement = conn.prepareStatement(query);
                pStatement.setInt(1, getMaxCustomerId());
                pStatement.setString(2, customerName);
                pStatement.setInt(3, getSelectedAddressId());
                pStatement.setInt(4, activeCustomer());
                pStatement.setString(5, UserData.getCurrentUser());
                pStatement.setString(6, UserData.getCurrentUser());

                pStatement.executeUpdate();

                resetAllCustomersRecords();
                Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerRecordsScreen.fxml"));
                Scene scene = new Scene(parent);

                Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

                screen.setScene(scene);
                screen.show();

                CityData.resetAll();
                CountryData.resetAll();
                CustomerRecordsData.resetAll();

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

            String name = selectedCustomer.getCustomerName();
            String[] split = name.split("\\s+");

            String firstName = split[0];
            String lastName = split[1];

            firstNameTextField.setText(firstName);
            lastNameTextField.setText(lastName);
        }
    }

}
