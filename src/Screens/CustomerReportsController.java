/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.Appointment;
import Model.AppointmentData;
import Model.CityData;
import Model.CountryData;
import Model.CustomerRecords;
import Model.CustomerReportsData;
import Model.UserData;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class CustomerReportsController implements Initializable {

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> consultantComboBox;

    @FXML
    private TableView<Appointment> scheduleTableView;

    @FXML
    private Label countLabel;

    @FXML
    private Label countTypesLabel;

    @FXML
    private TableColumn<Appointment, String> startColumn;

    @FXML
    private TableColumn<Appointment, String> endColumn;

    public void exitCalendarButtonPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Reports Screen Confirmation");
        alert.setContentText("Are you sure you want to exit the reports screen?");

        Optional<ButtonType> results = alert.showAndWait();

        if (results.get() == ButtonType.OK) {

            Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerRecordsScreen.fxml"));
            Scene scene = new Scene(parent);

            Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

            screen.setScene(scene);
            screen.show();

        }
    }

    public void getUser(ActionEvent e) {
        String user = consultantComboBox.getValue();

        AppointmentData.setAppointmentByUser(user);
        scheduleTableView.setItems(AppointmentData.getAppointmentByUser());

    }

    public void getMonth(ActionEvent e) {
        Integer month = monthComboBox.getSelectionModel().getSelectedIndex() + 1;

        CustomerReportsData.setTypesByMonth(month);
        countTypesLabel.setText("We have " + CustomerReportsData.getMonthCount().toString()
                + " type(s) of appointment(s) \n for the month of "
                + monthComboBox.getValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerReportsData.setMonths();
        CustomerReportsData.setAllCustomers();
        CustomerReportsData.setNames();
        CustomerReportsData.setCountYear();

        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        monthComboBox.setItems(CustomerReportsData.getMonths());
        consultantComboBox.setItems(UserData.getAllUserNames());

        countLabel.setText("We have " + CustomerReportsData.getCount().toString()
                + " appointments for the year.");

    }

}
