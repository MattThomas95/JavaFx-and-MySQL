package Model;

import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentData {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ArrayList<String> appointmentType = new ArrayList<String>();
    private static ArrayList<String> endTimeList = new ArrayList<String>();
    private static ArrayList<Integer> updateList = new ArrayList<Integer>();
    private static ArrayList<Integer> lengthList = new ArrayList<Integer>();
    private static ObservableList<String> allTimes = FXCollections.observableArrayList();
    private static ObservableList<Appointment> selectedAppointment = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsByUser = FXCollections.observableArrayList();

    public static void setAllAppointments() {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT a.appointmentId, a.type, a.start, a.end, b.customerId, b.customerName\n"
                    + "FROM appointment As a\n"
                    + "INNER JOIN customer AS b\n"
                    + "ON a.customerId = b.customerId;");

            while (set.next()) {
                String zonedStart = setZonedTime(set.getString(3));
                String zonedEnd = setZonedTime(set.getString(4));

                Appointment newAppointment = new Appointment(set.getInt(1), set.getString(6), set.getString(2), zonedStart, zonedEnd);

                allAppointments.add(newAppointment);
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    public static void resetAllAppointments() {
        allAppointments.removeAll(allAppointments);
        setAllAppointments();
    }

    public static void setAppointmentType(String type) {
        appointmentType.add(type);
    }

    public static String getAppointmentType() {
        return appointmentType.get(0);
    }

    public static void resetAppointmentType() {
        appointmentType.removeAll(appointmentType);
    }

    public static String getNotNeeded() {
        return "not needed";
    }

    public static void setEndTime(String time) {
        endTimeList.add(time);
    }

    public static String getEndTime() {
        return endTimeList.get(0);
    }

    public static void resetEndTimeList() {
        endTimeList.removeAll(endTimeList);
    }

    public static void deleteAppointment(Appointment appointment) {

        allAppointments.remove(appointment);
        try {
            Class.forName(mySQLjdbcDriver);
            String query = ("DELETE FROM appointment WHERE appointmentId = ?;");
            PreparedStatement pStatement = conn.prepareStatement(query);
            pStatement.setInt(1, appointment.getAppointmentId());
            pStatement.executeUpdate();

            resetAllAppointments();

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setTimes() {
        allTimes.add("09:00");
        allTimes.add("09:30");
        allTimes.add("10:00");
        allTimes.add("10:30");
        allTimes.add("11:00");
        allTimes.add("11:30");
        allTimes.add("12:00");
        allTimes.add("12:30");
        allTimes.add("13:00");
        allTimes.add("13:30");
        allTimes.add("14:00");
        allTimes.add("14:30");
        allTimes.add("15:00");
        allTimes.add("15:30");
        allTimes.add("16:00");
        allTimes.add("16:30");
    }

    public static ObservableList<String> getTimes() {
        return allTimes;
    }

    public static void updateButtonClicked() {
        updateList.add(1);
    }

    public static int getUpdateStatus() {
        return updateList.get(0);
    }

    public static boolean getUpdateBoolean() {
        return updateList.isEmpty();
    }

    public static void resetUpdateStatus() {
        updateList.removeAll(updateList);
    }

    public static void setSelectedAppointment(Appointment appointment) {
        selectedAppointment.add(appointment);
    }

    public static Appointment getSelectedAppointment() {
        return selectedAppointment.get(0);
    }

    public static void resetSelectedAppointment() {
        selectedAppointment.removeAll(selectedAppointment);
    }

    public static void setLengthRadio(Integer length) {
        lengthList.add(length);
    }

    public static Integer getLengthRadio() {
        return lengthList.get(0);
    }

    public static void resetLengthRadio() {
        lengthList.removeAll(lengthList);
    }

    public static String setZonedTime(String time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        LocalDateTime ldt = LocalDateTime.parse(time, formatter);

        ZoneId currentZone = ZoneId.of("America/New_York");

        ZonedDateTime currentTime = ldt.atZone(currentZone);

        ZoneId newZone = ZoneId.systemDefault();

        ZonedDateTime zonedTime = currentTime.withZoneSameInstant(newZone);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        time = formatter2.format(zonedTime);

        return time;

    }

    public static void setAppointmentByUser(String user) {
        try {
            Class.forName(mySQLjdbcDriver);
            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM appointment WHERE createdBy = '"
                    + user + "';");

            while (set.next()) {
                String zonedStart = setZonedTime(set.getString(10));
                String zonedEnd = setZonedTime(set.getString(11));

                Appointment newAppointment = new Appointment(set.getInt(1), "test", set.getString(8), zonedStart, zonedEnd);
                appointmentsByUser.add(newAppointment);
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ObservableList<Appointment> getAppointmentByUser() {
        return appointmentsByUser;
    }

    public static void resetAppointmentByUser() {
        appointmentsByUser.removeAll(appointmentsByUser);
    }

    public static void resetAll() {
        AppointmentData.resetAllAppointments();
        AppointmentData.resetAppointmentType();
        AppointmentData.resetEndTimeList();
        AppointmentData.resetLengthRadio();
        AppointmentData.resetSelectedAppointment();
        AppointmentData.resetUpdateStatus();
        AppointmentData.resetAppointmentByUser();
    }
}
