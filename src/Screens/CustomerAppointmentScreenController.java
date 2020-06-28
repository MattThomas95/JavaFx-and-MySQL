/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.Appointment;
import Model.AppointmentData;
import Model.CalendarData;
import Model.CityData;
import Model.CountryData;
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
public class CustomerAppointmentScreenController implements Initializable {

    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, String> nameColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> startColumn;
    @FXML
    private TableColumn<Appointment, String> endColumn;
    @FXML
    Appointment selectedAppointment;

    public void addAppointmentButtonPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Screens/SelectCustomerScreen.fxml"));
        Scene scene = new Scene(parent);

        Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

        screen.setScene(scene);
        screen.show();
    }

    @FXML
    public void deleteAppointmentButtonPushed(ActionEvent event) throws IOException {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        AppointmentData.deleteAppointment(selectedAppointment);
    }

    @FXML
    public void updateAppointmentButtonPushed(ActionEvent event) throws IOException {
        String name = appointmentTable.getSelectionModel().getSelectedItem().getName();
        CustomerRecordsData.setCustomerNameApp(name);
        CustomerRecordsData.getCustomerNameApp();
        AppointmentData.updateButtonClicked();
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        AppointmentData.setSelectedAppointment(selectedAppointment);
        Parent parent = FXMLLoader.load(getClass().getResource("/Screens/SelectCustomerScreen.fxml"));
        Scene scene = new Scene(parent);

        Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

        screen.setScene(scene);
        screen.show();
    }

    public void exitAppointmentButtonPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Appointment Screen Confirmation");
        alert.setContentText("Are you sure you want to exit the appointment screen?");

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

    public void calendarButtonPushed(ActionEvent event) throws IOException {
        CalendarData.resetAll();
        CalendarData.setCalendarMonth();
        CalendarData.setCalendarWeek();

        Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerCalendar.fxml"));
        Scene scene = new Scene(parent);

        Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

        screen.setScene(scene);
        screen.show();

        CityData.removeCitiesInCountry();
        CityData.resetSelectedCity();
        CountryData.resetIndex();
        CustomerRecordsData.resetCustomersCity();
        CustomerRecordsData.resetCustomersCountry();
        CustomerRecordsData.resetSelectedCustomer();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));

        appointmentTable.setItems(AppointmentData.getAllAppointments());

    }

}
