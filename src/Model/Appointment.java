package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {

    private SimpleIntegerProperty appointmentId;
    private SimpleStringProperty name;
    private SimpleStringProperty type;
    private SimpleStringProperty start;
    private SimpleStringProperty end;

    public Appointment(Integer appointmentId, String name, String type, String start, String end) {
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.start = new SimpleStringProperty(start);
        this.end = new SimpleStringProperty(end);
    }

    public Integer getAppointmentId() {
        return appointmentId.get();
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }

    public String getStart() {
        return start.get();
    }

    public void setStart(String start) {
        this.start = new SimpleStringProperty(start);
    }

    public String getEnd() {
        return end.get();
    }

    public void setEnd(String end) {
        this.end = new SimpleStringProperty(end);
    }
}
