/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Model.CalendarData;
import Model.UserData;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class LoginScreenController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label loginLabel1;
    @FXML
    private Label loginLabel2;
    @FXML
    private Button loginButton;

    public void loginButtonPushed(ActionEvent event) throws IOException {
        if (UserData.loginValadation(usernameField.getText(), passwordField.getText()) == true) {
            UserData.setCurrentUser(usernameField.getText());
            Parent parent = FXMLLoader.load(getClass().getResource("/Screens/CustomerRecordsScreen.fxml"));
            Scene scene = new Scene(parent);

            Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();

            screen.setScene(scene);
            screen.show();
            CalendarData.checkAppointmentTimes();
            java.util.Date date = new java.util.Date();
            try (FileWriter fw = new FileWriter("timeStamps.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter timeStamps = new PrintWriter(bw)) {
                timeStamps.println("User '" + UserData.getUserName() + "' logged on " + date + "\n");
            } catch (IOException e) {
            }
        } else {
            if (Locale.getDefault().getLanguage().equals("es")) { //getLanguage
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Error de inicio de sesión");
                alert.setContentText("Nombre de usuario o contraseña incorrecta.");
                alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setContentText("Incorrect username or password.");
                alert.showAndWait();
            }
        }

    }

    // 2nd lambda used to get language based on location
    interface getLocation {

        public Locale location();
    }

    public static void locationCheck() {
        getLocation l = () -> {
            Locale.getDefault();
            return Locale.getDefault();
        };
        System.out.println("Language is '" + l.location() + "'");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Locale.getDefault().getLanguage().equals("es")) {
            loginLabel1.setText("¡Bienvenidos!");
            loginLabel2.setText("Inicia sesión para continuar");
            usernameField.setPromptText("Nombre de usuario");
            passwordField.setPromptText("Contraseña");
            loginButton.setText("Iniciar sesión");
        }
    }

}
