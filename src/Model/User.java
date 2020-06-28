package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {

    private SimpleIntegerProperty userId;
    private SimpleStringProperty userName;
    private SimpleStringProperty password;

    public User(Integer userId, String userName, String password) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(userName);

    }

    public Integer getUserId() {
        return userId.get();
    }

    public void setUserId(Integer userId) {
        this.userId = new SimpleIntegerProperty(userId);
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName = new SimpleStringProperty(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

}
