/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import static Model.AddressData.setAllAddresses;
import static Model.AppointmentData.setAllAppointments;
import static Model.AppointmentData.setTimes;
import Model.CalendarData;
import static Model.CalendarData.addTimes;
import static Model.CalendarData.setCalendarDay;
import static Model.CountryData.setAllCountries;
import static Model.CustomerData.setAllCustomers;
import static Model.CustomerRecordsData.setAllCustomerRecords;
import Model.DatabaseConnection;
import Model.UserData;
import static Model.UserData.getLoginInfo;
import java.time.ZoneId;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author James
 */
public class MathewThomasC195 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Screens/LoginScreen.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //I chose spanish as my second language
        //for my 1st lambda I used runnable to run in same thread
        Runnable run = () -> {
            DatabaseConnection.startConnection();
            LoginScreenController.locationCheck();
            getLoginInfo();
            setAllCustomers();
            setAllAddresses();
            setAllCountries();
            setAllCustomerRecords();
            setAllAppointments();
            setTimes();
            setCalendarDay();
            addTimes();
            System.out.println("Current timezone is '" + ZoneId.systemDefault() + "'");
            launch(args);
            DatabaseConnection.closeConnection();
        };
        run.run();
    }
}
