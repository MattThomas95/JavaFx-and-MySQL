/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.CalendarData;
import Model.CustomerRecords;
import Model.CustomerRecordsData;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class CustomerRecordsScreenController implements Initializable {

    @FXML
    private TableView<CustomerRecords> customerNameTable;
    @FXML
    private TableColumn<CustomerRecords, String> customerNameColumn;
    @FXML
    private TableColumn<CustomerRecords, String> addressColumn;
    @FXML
    private TableColumn<CustomerRecords, String> phoneNumberColumn;
    @FXML
    private CustomerRecords selectedCustomer;

    public void addCustomerButtonPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Screens/AddCustomerCountry.fxml"));
        Scene scene = new Scene(parent);

        Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

        screen.setScene(scene);
        screen.show();

        CustomerRecordsData.resetAll();
    }

    public void deleteCustomerButtonPushed(ActionEvent event) throws IOException {

        selectedCustomer = customerNameTable.getSelectionModel().getSelectedItem();

        CustomerRecordsData.deleteSelectedCustomer(selectedCustomer);

        CustomerRecordsData.resetAll();
    }

    public void updateCustomerButtonPushed(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Screens/AddCustomerCountry.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        AddCustomerCountryController controller = loader.getController();

        Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

        controller.initializeData(customerNameTable.getSelectionModel().getSelectedItem());

        screen.setScene(scene);
        screen.show();

        CustomerRecordsData.updateButtonClicked();

        CustomerRecordsData.setSelectedCustomer(customerNameTable.getSelectionModel().getSelectedItem());

        System.out.println("Update status " + CustomerRecordsData.getUpdateStatus());
    }

    public void appointmentButtonPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerAppointmentScreen.fxml"));
        Scene scene = new Scene(parent);

        Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

        screen.setScene(scene);
        screen.show();

        CustomerRecordsData.resetAll();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        customerNameTable.setItems(CustomerRecordsData.getAllCustomerRecords());

    }

}
