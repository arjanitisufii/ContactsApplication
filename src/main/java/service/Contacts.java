package service;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contacts {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleLongProperty phone = new SimpleLongProperty();
    private SimpleStringProperty location = new SimpleStringProperty();

    public Contacts() {
    }

    public Contacts(String name, long phone) {
        this(name, phone, "Unknown");
    }

    public Contacts(String name, long phone, String location) {
        this.name.set(name);
        this.phone.set(phone);
        this.location.set(location);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPhone(long phone) {
        this.phone.set(phone);
    }

    public String getName() {
        return name.get();
    }

    public long getPhone() {
        return phone.get();
    }

    @Override
    public String toString() {
        return name.get() + " : " + phone.get();
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash += 3 * name.get().hashCode();
        hash += 3 * Long.valueOf(phone.get()).hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Contacts) {
            Contacts contact = (Contacts) obj;
            return contact.getName().equalsIgnoreCase(name.get()) && contact.getPhone() == phone.get();
        }
        return false;
    }


}
