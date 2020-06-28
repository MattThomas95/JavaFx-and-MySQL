package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {

    private SimpleIntegerProperty customerId;
    private SimpleStringProperty customerName;

    public Customer(Integer customerId, String customerName) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerName = new SimpleStringProperty(customerName);
    }

    public Integer getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = new SimpleIntegerProperty(customerId);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName = new SimpleStringProperty(customerName);
    }

}
