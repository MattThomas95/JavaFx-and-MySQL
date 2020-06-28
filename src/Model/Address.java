package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Address {

    private SimpleIntegerProperty addressId;
    private SimpleStringProperty address;
    private SimpleStringProperty address2;
    private SimpleIntegerProperty postalCode;
    private SimpleStringProperty phoneNumber;

    public Address(Integer addressId, String address, String address2, Integer postalCode, String phoneNumber) {
        this.addressId = new SimpleIntegerProperty(addressId);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.postalCode = new SimpleIntegerProperty(postalCode);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public Integer getAddressId() {
        return addressId.get();
    }

    public void setCustomerId(Integer addressId) {
        this.addressId = new SimpleIntegerProperty(addressId);
    }

    public String getAddress() {
        return address.get();
    }

    public void setCustomerName(String address) {
        this.address = new SimpleStringProperty(address);
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
