package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerRecords {

    private SimpleIntegerProperty customerId;
    private SimpleStringProperty customerName;
    private SimpleIntegerProperty addressId;
    private SimpleStringProperty address;
    private SimpleStringProperty address2;
    private SimpleIntegerProperty postalCode;
    private SimpleStringProperty phoneNumber;

    public CustomerRecords(Integer customerId, String customerName, Integer addressId, String address,
            String address2, Integer postalCode, String phoneNumber) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerName = new SimpleStringProperty(customerName);
        this.addressId = new SimpleIntegerProperty(addressId);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.postalCode = new SimpleIntegerProperty(postalCode);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
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

    public Integer getAddressId() {
        return addressId.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getAddress2() {
        return address2.get();
    }

    public void setAddress2(String address2) {
        this.address2 = new SimpleStringProperty(address2);
    }

    public Integer getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = new SimpleIntegerProperty(postalCode);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

}
