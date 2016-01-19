package no.ingesen.martin.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address{

    @NotNull
    @Length(max=64, message="The country can't be any longer than 64 characters.")
    private String country;

    @NotNull
    @Length(max=64, message="The state can't be any longer than 64 characters.")
    private String state;

    @NotNull
    @Length(max=64, message="The city can't be any longer than 64 characters.")
    private String city;

    @NotNull
    @Length(max=64, message="The street can't be any longer than 64 characters.")
    private String street;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
}
