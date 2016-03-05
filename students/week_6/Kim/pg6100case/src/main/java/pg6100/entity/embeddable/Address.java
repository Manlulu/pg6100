package pg6100.entity.embeddable;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String state;
    private String city;
    private String Street;

    public Address() {
    }  // Embeddable MÅ ha en tom konstruktør

    public Address(String state, String city, String street) {
        this.state = state;
        this.city = city;
        Street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }
}