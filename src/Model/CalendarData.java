package Model;

import static Model.AppointmentData.setZonedTime;
import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class CalendarData {

    private static ObservableList<Appointment> weekView = FXCollections.observableArrayList();
    private static ObservableList<Appointment> monthView = FXCollections.observableArrayList();
    private static ObservableList<Appointment> dayView = FXCollections.observableArrayList();
    private static ArrayList<String> startTimesList = new ArrayList<String>();
    private static ArrayList<String> startTimesListOverLap = new ArrayList<String>();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static void setCalendarWeek() {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT a.appointmentId, a.type, a.start, a.end, b.customerId, b.customerName\n"
                    + "FROM appointment As a\n"
                    + "INNER JOIN customer AS b\n"
                    + "ON a.customerId = b.customerId\n"
                    + "WHERE WEEKOFYEAR(start)=WEEKOFYEAR(NOW());");

            while (set.next()) {

                String zonedStart = setZonedTime(set.getString(3));
                String zonedEnd = setZonedTime(set.getString(4));

                Appointment newAppointment = new Appointment(set.getInt(1), set.getString(6), set.getString(2), zonedStart, zonedEnd);

                weekView.add(newAppointment);
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ObservableList<Appointment> getCalendarWeek() {
        return weekView;
    }

    public static void resetCalendarWeek() {
        weekView.removeAll(weekView);
    }

    public static void setCalendarMonth() {

        ObservableList<Appointment> a = AppointmentData.getAllAppointments();

        ObservableList<Appointment> f = a.filtered(app -> {
            return true;
        });
        //monthView.addAll(f);
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT a.appointmentId, a.type, a.start, a.end, b.customerId, b.customerName\n"
                    + "FROM appointment As a\n"
                    + "INNER JOIN customer AS b\n"
                    + "ON a.customerId = b.customerId\n"
                    + "WHERE MONTH(start)=MONTH(NOW())\n"
                    + "AND YEAR(start)=YEAR(NOW());");

            while (set.next()) {

                String zonedStart = setZonedTime(set.getString(3));
                String zonedEnd = setZonedTime(set.getString(4));

                Appointment newAppointment = new Appointment(set.getInt(1), set.getString(6), set.getString(2), zonedStart, zonedEnd);

                monthView.add(newAppointment);
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ObservableList<Appointment> getCalendarMonth() {
        return monthView;
    }

    public static void resetCalendarMonth() {
        monthView.removeAll(monthView);
    }

    public static void setCalendarDay() {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT a.appointmentId, a.type, a.start, a.end, b.customerId, b.customerName, DATE_FORMAT(start, '%Y-%m-%d')\n"
                    + "FROM appointment As a\n"
                    + "INNER JOIN customer AS b\n"
                    + "ON a.customerId = b.customerId\n"
                    + "WHERE DATE(start)= CURDATE();");

            while (set.next()) {

                String zonedStart = setZonedTime(set.getString(3));
                String zonedEnd = setZonedTime(set.getString(4));

                Appointment newAppointment = new Appointment(set.getInt(1), set.getString(6), set.getString(2), zonedStart, zonedEnd);

                dayView.add(newAppointment);
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ObservableList<Appointment> getCalendarDay() {
        return monthView;
    }

    public static void resetCalendarDay() {
        monthView.removeAll(monthView);
    }

    public static void addTimes() {
        for (Appointment currentData : dayView) {
            String today = currentData.getStart();
            String[] split = today.split(" ", 2);
            startTimesList.add(split[1]);
        }
    }

    public static ArrayList<String> getTimes() {
        return startTimesList;
    }

    public static void setAllAppointments() {
        allAppointments.addAll(AppointmentData.getAllAppointments());
    }

    public static void getAllTimes() {
        for (Appointment appointment : allAppointments) {
            startTimesListOverLap.add(appointment.getStart());
        }
    }

    public static void resetAllTimes() {
        allAppointments.removeAll(allAppointments);
        startTimesListOverLap.removeAll(startTimesListOverLap);
    }

    public static String getCurrentTime() {
        Date currentDate = Calendar.getInstance(TimeZone.getDefault()).getTime();
        String[] split = currentDate.toString().split(" ", 5);

        String time = split[3];
        return time;
    }

    public static void checkAppointmentTimes() {
        try {
            for (String times : startTimesList) {
                String currentTimeString = getCurrentTime();
                Date currentTime = new SimpleDateFormat("HH:mm:ss").parse(currentTimeString);
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(currentTime);
                currentCalendar.add(Calendar.DATE, 1);

                String timePlus15String = getCurrentTime();
                Date timePlus15 = new SimpleDateFormat("HH:mm:ss").parse(timePlus15String);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(timePlus15);
                calendar2.add(Calendar.DATE, 1);
                calendar2.add(Calendar.MINUTE, 15);

                String appointmentTimes = times;
                Date appointmentDate = new SimpleDateFormat("HH:mm:ss").parse(appointmentTimes);
                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(appointmentDate);
                calendar3.add(Calendar.DATE, 1);

                Date x = calendar3.getTime();
                if (x.after(currentCalendar.getTime()) && x.before(calendar2.getTime())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Customer Appointment Reminder");
                    alert.setContentText("You have an appointment in 15 minutes or less!");

                    Optional<ButtonType> results = alert.showAndWait();

                    if (results.get() == ButtonType.OK) {
                        alert.close();

                    }
                }
            }
        } catch (ParseException e) {
        }

    }

    public static boolean checkOverLap(String startTime) {
        try {
            resetAllTimes();
            setAllAppointments();
            getAllTimes();

            for (String times : startTimesListOverLap) {
                String currentTimeString = startTime;
                Date currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentTimeString);
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(currentTime);

                String appointmentTimes = times;
                Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(appointmentTimes);
                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(appointmentDate);

                if (currentCalendar.getTime().equals(calendar3.getTime())) {
                    return false;
                }
            }
        } catch (ParseException e) {
        }
        return true;
    }

    public static void resetAll() {
        CalendarData.resetCalendarMonth();
        CalendarData.resetCalendarWeek();
        CalendarData.resetCalendarDay();
    }

}
