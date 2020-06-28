package Model;

import static Model.DatabaseConnection.conn;
import static Model.DatabaseConnection.jdbcURL;
import static Model.DatabaseConnection.mySQLjdbcDriver;
import static Model.DatabaseConnection.password;
import static Model.DatabaseConnection.userName;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserData {

    private static ObservableList userNameList = FXCollections.observableArrayList();
    private static ObservableList passwordList = FXCollections.observableArrayList();
    private static ArrayList<String> currentUserList = new ArrayList<String>();
    private static ArrayList<Integer> currentUserId = new ArrayList<Integer>();
    private static ObservableList<String> allUserNames = FXCollections.observableArrayList();

    public static void setCurrentUser(String currentUser) {
        currentUserList.add(currentUser);
    }

    public static String getCurrentUser() {
        return currentUserList.get(0);
    }

    public static void setCurrentUserId(Integer currentUser) {
        currentUserId.add(currentUser);
    }

    public static Integer getCurrentUserId() {
        return currentUserId.get(0);
    }

    public static void addUserName(String newUserName) {
        userNameList.add(newUserName);
    }

    public static String getUserName() {
        return userNameList.get(0).toString();
    }

    public static void addPassword(String newPassword) {
        passwordList.add(newPassword);
    }

    public static boolean loginValadation(String userInput, String userPasswordInput) {
        if (userNameList.contains(userInput) && passwordList.contains(userPasswordInput)) {
            return true;
        }
        return false;
    }

    public static void getLoginInfo() {
        // 

        try {
            Class.forName(mySQLjdbcDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);

            Statement statement = conn.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM user;");

            while (set.next()) {
                User newUser = new User(set.getInt(1), set.getString(2), set.getString(3));
                setCurrentUserId(set.getInt(1));
                addUserName(set.getString(2));
                addPassword(set.getString(3));
                allUserNames.add(set.getString(2));
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ObservableList<String> getAllUserNames() {
        return allUserNames;

    }

}
