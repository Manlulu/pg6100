package ninja.idar.models;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
@Embeddable
public class Address {
    @NotNull(message = "Address requires a country")
    private String country;
    @NotNull(message = "Address requires a state")
    private String state;
    @NotNull(message = "Address requires a city")
    private String city;
    @NotNull(message = "Address requires a street")
    private String street;

    public Address() {
    }

    public Address(String country, String state, String city, String street) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
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
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
