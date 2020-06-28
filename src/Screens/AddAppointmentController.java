/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.AddressData;
import Model.Appointment;
import Model.AppointmentData;
import Model.CalendarData;
import Model.CityData;
import Model.CountryData;
import Model.CustomerData;
import Model.CustomerRecordsData;
import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.jdbcURL;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import static Model.DatabaseConnection.password;
import static Model.DatabaseConnection.userName;
import static Model.GetMax.getMaxAppointmentId;
import Model.UserData;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class AddAppointmentController implements Initializable {

    @FXML
    private RadioButton phoneCall;
    @FXML
    private RadioButton interview;
    @FXML
    private RadioButton meeting;
    @FXML
    private RadioButton training;
    @FXML
    private ToggleGroup appointmentTypes;
    @FXML
    private RadioButton fifteenMinute;
    @FXML
    private RadioButton thirtyMinute;
    @FXML
    private RadioButton fortyFiveMinute;
    @FXML
    private RadioButton sixtyMinute;
    @FXML
    private ToggleGroup minutes;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label dateLabel;
    @FXML
    private Label dayLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label endLabel;
    @FXML
    private ComboBox<String> timeComboBox;
    @FXML
    private Appointment selectedAppointment;

    public void exitAppointmentButtonPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Appointment Screen Confirmation");
        alert.setContentText("Are you sure you want to exit the appointment screen?");

        Optional<ButtonType> results = alert.showAndWait();

        if (results.get() == ButtonType.OK) {

            Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerAppointmentScreen.fxml"));
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

    public void getDate(ActionEvent e) {
        LocalDate date = datePicker.getValue();
        dateLabel.setText(date.toString());
        dayLabel.setText(date.getDayOfWeek().toString());
    }

    public void getType(ActionEvent e) {
        if (meeting.isSelected()) {
            typeLabel.setText("Meeting");
        } else if (interview.isSelected()) {
            typeLabel.setText("Interview");
        } else if (training.isSelected()) {
            typeLabel.setText("Training");
        } else {
            typeLabel.setText("Phone Call");
        }
    }

    public void getTime() {
        String time = timeComboBox.getValue();
        timeLabel.setText(time);

        String[] split = time.split(":", 3);

        Integer hour = Integer.parseInt(split[0]);
        String minute = split[1];
        String newMinute;
        Integer newHour;

        if (fifteenMinute.isSelected() && minute.contains("00")) {
            newMinute = "15";
            String endTime = hour + ":" + newMinute;
            endLabel.setText(endTime);
            AppointmentData.resetEndTimeList();
            AppointmentData.setEndTime(endTime);
        }
        if (fifteenMinute.isSelected() && minute.contains("30")) {
            newMinute = "45";
            String endTime = hour + ":" + newMinute;
            endLabel.setText(endTime);
            AppointmentData.resetEndTimeList();
            AppointmentData.setEndTime(endTime);
        }
        if (thirtyMinute.isSelected() && minute.contains("00")) {
            newMinute = "30";
            String endTime = hour + ":" + newMinute;
            endLabel.setText(endTime);
            AppointmentData.resetEndTimeList();
            AppointmentData.setEndTime(endTime);
        }
        if (thirtyMinute.isSelected() && minute.contains("30")) {
            newMinute = "00";
            newHour = hour + 1;
            String endTime = newHour + ":" + newMinute;
            endLabel.setText(endTime);
            AppointmentData.resetEndTimeList();
            AppointmentData.setEndTime(endTime);
        }
        if (fortyFiveMinute.isSelected() && minute.contains("00")) {
            newMinute = "45";
            String endTime = hour + ":" + newMinute;
            endLabel.setText(endTime);
            AppointmentData.resetEndTimeList();
            AppointmentData.setEndTime(endTime);
        }
        if (fortyFiveMinute.isSelected() && minute.contains("30")) {
            newMinute = "15";
            newHour = hour + 1;
            String endTime = newHour + ":" + newMinute;
            endLabel.setText(endTime);
            AppointmentData.resetEndTimeList();
            AppointmentData.setEndTime(endTime);
        }
        if (sixtyMinute.isSelected() && minute.contains("00")) {
            newMinute = "00";
            newHour = hour + 1;
            String endTime = newHour + ":" + newMinute;
            endLabel.setText(endTime);
            AppointmentData.resetEndTimeList();
            AppointmentData.setEndTime(endTime);
        }
        if (sixtyMinute.isSelected() && minute.contains("30")) {
            newMinute = "30";
            newHour = hour + 1;
            String endTime = newHour + ":" + newMinute;
            endLabel.setText(endTime);
            AppointmentData.resetEndTimeList();
            AppointmentData.setEndTime(endTime);
        }

    }

    public void addAppointmentSaveButtonPushed(ActionEvent event) throws IOException {
        if (AppointmentData.getUpdateBoolean() == false) {
            AppointmentData.deleteAppointment(AppointmentData.getSelectedAppointment());
        }

        String type;
        if (phoneCall.isSelected()) {
            type = "Phone Call";
        } else if (interview.isSelected()) {
            type = "Interview";
        } else if (meeting.isSelected()) {
            type = "Meeting";
        } else {
            type = "Training";
        }

        LocalDate date;
        String time;
        String startTime;
        String endTime;
        date = datePicker.getValue();
        time = timeComboBox.getValue();
        endTime = AppointmentData.getEndTime();

        String[] split = time.split(" ", 2);
        String[] splitEnd = endTime.split(" ", 2);

        time = split[0];
        endTime = splitEnd[0];

        String[] splitEnd2 = endTime.split(":", 2);
        Integer endHour = Integer.parseInt(splitEnd2[0]);

        startTime = date + " " + time + ":00";

        if (endHour > 9) {
            endTime = date + " " + endTime + ":00";
        } else {
            endTime = date + " " + "0" + endTime + ":00";
        }

        if (CalendarData.checkOverLap(startTime) == false) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Overlap Error");
            alert.setContentText("You already have an appointment scheduled at this time.");

            Optional<ButtonType> results = alert.showAndWait();

            if (results.get() == ButtonType.OK) {
                alert.close();

            }
        } else {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime ldt = LocalDateTime.parse(startTime, formatter);
            LocalDateTime ldtEnd = LocalDateTime.parse(endTime, formatter);
            ZoneId currentZone = ZoneId.systemDefault();

            ZonedDateTime currentTime = ldt.atZone(currentZone);
            ZonedDateTime currentTimeEnd = ldtEnd.atZone(currentZone);
            ZoneId newZone = ZoneId.of("America/New_York");

            ZonedDateTime zonedTime = currentTime.withZoneSameInstant(newZone);
            ZonedDateTime zonedTimeEnd = currentTimeEnd.withZoneSameInstant(newZone);

            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            startTime = formatter2.format(zonedTime);
            endTime = formatter2.format(zonedTimeEnd);

            try {
                int index = getMaxAppointmentId();
                System.out.println("Appointment index is " + index);

                Class.forName(mySQLjdbcDriver);
                conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
                String query = ("INSERT INTO appointment"
                        + "(appointmentId, customerId, userId, title, description, location, contact, type, url, start, end, "
                        + "createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?);");
                PreparedStatement pStatement = conn.prepareStatement(query);
                pStatement.setInt(1, index);
                pStatement.setInt(2, CustomerData.getSelectedCustomerId());
                pStatement.setInt(3, UserData.getCurrentUserId());
                pStatement.setString(4, AppointmentData.getNotNeeded());
                pStatement.setString(5, AppointmentData.getNotNeeded());
                pStatement.setString(6, AppointmentData.getNotNeeded());
                pStatement.setString(7, AppointmentData.getNotNeeded());
                pStatement.setString(8, type);
                pStatement.setString(9, AppointmentData.getNotNeeded());
                pStatement.setString(10, startTime);
                pStatement.setString(11, endTime);
                pStatement.setString(12, UserData.getCurrentUser());
                pStatement.setString(13, UserData.getCurrentUser());

                pStatement.executeUpdate();
                AddressData.resetAll();
                CustomerRecordsData.resetAll();
                AppointmentData.resetAll();
                CalendarData.resetAll();

                Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerAppointmentScreen.fxml"));
                Scene scene = new Scene(parent);

                Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

                screen.setScene(scene);
                screen.show();

            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentTypes = new ToggleGroup();
        minutes = new ToggleGroup();
        this.interview.setToggleGroup(appointmentTypes);
        this.phoneCall.setToggleGroup(appointmentTypes);
        this.meeting.setToggleGroup(appointmentTypes);
        this.training.setToggleGroup(appointmentTypes);
        this.phoneCall.setSelected(true);
        this.fifteenMinute.setToggleGroup(minutes);
        this.thirtyMinute.setToggleGroup(minutes);
        this.fortyFiveMinute.setToggleGroup(minutes);
        this.sixtyMinute.setToggleGroup(minutes);
        this.fifteenMinute.setSelected(true);

        typeLabel.setText("Phone Call");
        datePicker.setValue(LocalDate.now());
        dateLabel.setText(LocalDate.now().toString());
        dayLabel.setText(LocalDate.now().getDayOfWeek().toString());
        timeComboBox.setItems(AppointmentData.getTimes());

        if (AppointmentData.getUpdateBoolean() == false) {
            selectedAppointment = AppointmentData.getSelectedAppointment();
            String[] splitStart = selectedAppointment.getStart().split(" ", 2);
            String[] splitEnd = selectedAppointment.getEnd().split(" ", 2);

            String dateStart = splitStart[0];
            String timeStart = splitStart[1];

            String[] splitStart2 = timeStart.split(":", 3);
            Integer hour = Integer.parseInt(splitStart2[0]);
            String minute = splitStart2[1];
            String selectedTime;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime time = LocalDateTime.parse(selectedAppointment.getStart(), formatter);
            LocalDateTime time2 = LocalDateTime.parse(selectedAppointment.getEnd(), formatter);

            long timeInMinutes = java.time.Duration.between(time, time2).toMinutes();

            if (timeInMinutes == 15) {
                this.fifteenMinute.setSelected(true);
            } else if (timeInMinutes == 30) {
                this.thirtyMinute.setSelected(true);
            } else if (timeInMinutes == 45) {
                this.fortyFiveMinute.setSelected(true);
            } else {
                this.sixtyMinute.setSelected(true);
            }
            if (hour > 9) {
                selectedTime = hour + ":" + minute;
            } else {
                selectedTime = "0" + hour + ":" + minute;
            }

            LocalDate localDate = LocalDate.parse(dateStart);
            LocalDateTime ldt = localDate.atStartOfDay();

            if (selectedAppointment.getType().contains("Phone Call")) {
                this.phoneCall.setSelected(true);
            } else if (selectedAppointment.getType().contains("Interview")) {
                this.interview.setSelected(true);
            } else if (selectedAppointment.getType().contains("Meeting")) {
                this.meeting.setSelected(true);
            } else if (selectedAppointment.getType().contains("Training")) {
                this.training.setSelected(true);
            }

            typeLabel.setText(selectedAppointment.getType());
            datePicker.setValue(ldt.toLocalDate());
            dateLabel.setText(ldt.toLocalDate().toString());
            dayLabel.setText(ldt.toLocalDate().getDayOfWeek().toString());
            timeComboBox.getSelectionModel().select(selectedTime);

            getTime();

        }
    }
}
