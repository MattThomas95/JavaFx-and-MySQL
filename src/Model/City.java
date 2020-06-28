package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class City {

    private SimpleIntegerProperty cityId;
    private SimpleStringProperty city;

    public City(Integer cityId, String city) {
        this.cityId = new SimpleIntegerProperty(cityId);
        this.city = new SimpleStringProperty(city);
    }

    public Integer getCityId() {
        return cityId.get();
    }

    public void setCityId(Integer cityId) {
        this.cityId = new SimpleIntegerProperty(cityId);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city = new SimpleStringProperty(city);
    }

}
