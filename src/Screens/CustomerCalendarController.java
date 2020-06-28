/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.Appointment;
import Model.CalendarData;
import Model.CityData;
import Model.CountryData;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class CustomerCalendarController implements Initializable {

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
    private RadioButton weekButton;
    @FXML
    private RadioButton monthButton;
    @FXML
    private ToggleGroup group;
    @FXML
    private Label label;

    public void exitCalendarButtonPushed(ActionEvent event) throws IOException {
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

            CityData.resetAll();
            CountryData.resetAll();

        }
    }

    public void reportsButtonPushed(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerReports.fxml"));
        Scene scene = new Scene(parent);

        Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

        screen.setScene(scene);
        screen.show();

    }

    public void getSelectedButton(ActionEvent event) {
        if (weekButton.isSelected()) {
            appointmentTable.setItems(CalendarData.getCalendarWeek());
            appointmentTable.refresh();

            Date date = Calendar.getInstance().getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int week = calendar.get(Calendar.WEEK_OF_YEAR);

            label.setText("All Appointments for Week " + week);

        } else {
            appointmentTable.setItems(CalendarData.getCalendarMonth());
            appointmentTable.refresh();

            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String month = localDate.getMonth().toString();

            label.setText("All Appointments the Month of " + month);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        group = new ToggleGroup();
        this.weekButton.setToggleGroup(group);
        this.monthButton.setToggleGroup(group);
        this.weekButton.setSelected(true);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));

        appointmentTable.setItems(CalendarData.getCalendarWeek());

        Date date = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);

        label.setText("All Appointments for Week " + week);

    }

}
