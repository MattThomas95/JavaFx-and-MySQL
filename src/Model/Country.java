package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Country {

    private SimpleIntegerProperty countryId;
    private SimpleStringProperty country;

    public Country(Integer countryId, String country) {
        this.countryId = new SimpleIntegerProperty(countryId);
        this.country = new SimpleStringProperty(country);
    }

    public Integer getCountryId() {
        return countryId.get();
    }

    public void setCountryId(Integer countryId) {
        this.countryId = new SimpleIntegerProperty(countryId);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country = new SimpleStringProperty(country);
    }
}
