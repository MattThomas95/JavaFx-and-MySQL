/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.AppointmentData;
import Model.CityData;
import Model.CountryData;
import Model.CustomerData;
import Model.CustomerRecords;
import Model.CustomerRecordsData;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class SelectCustomerScreenController implements Initializable {

    @FXML
    private TableView<CustomerRecords> customerNameTable;
    @FXML
    private TableColumn<CustomerRecords, String> customerNameColumn;

    public void addAppointmentNextButtonPushed(ActionEvent event) throws IOException {
        int index = customerNameTable.getSelectionModel().getSelectedItem().getCustomerId();

        CustomerData.resetSelectedCustomerId();
        CustomerData.setSelectedCustomerId(index);
        System.out.println("Customer ID is " + CustomerData.getSelectedCustomerId());

        Parent parent = FXMLLoader.load(getClass().getResource("/Screens/AddAppointment.fxml"));
        Scene scene = new Scene(parent);

        Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

        screen.setScene(scene);
        screen.show();
    }

    public void addCustomerCancelButtonPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Appointment Confirmation");
        alert.setContentText("Are you sure you want to cancel adding this appointment?");

        Optional<ButtonType> results = alert.showAndWait();

        if (results.get() == ButtonType.OK) {

            Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerRecordsScreen.fxml"));
            Scene scene = new Scene(parent);

            Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

            screen.setScene(scene);
            screen.show();

            CityData.removeCitiesInCountry();
            CountryData.resetIndex();
            CustomerRecordsData.resetCustomersCity();
            CustomerRecordsData.resetCustomersCountry();
            CustomerRecordsData.resetSelectedCustomer();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        customerNameTable.setItems(CustomerRecordsData.getAllCustomerRecords());

        if (AppointmentData.getUpdateBoolean() == false) {
            customerNameTable.getSelectionModel().select(CustomerRecordsData.getSelectedCustomer());
        }

    }

}
