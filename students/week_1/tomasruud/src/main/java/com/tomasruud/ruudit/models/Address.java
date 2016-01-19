package com.tomasruud.ruudit.models;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Tomas Ruud
 * @since 18.01.2016
 */
@Embeddable
public class Address {

    @Size( max = 64 )
    private String state;

    @NotNull
    @Size( max = 64 )
    private String city;

    @NotNull
    @Size( max = 64 )
    private String street;

    public String getState() {

        return state;
    }

    public void setState( String state ) {

        this.state = state;
    }

    public String getCity() {

        return city;
    }

    public void setCity( String city ) {

        this.city = city;
    }

    public String getStreet() {

        return street;
    }

    public void setStreet( String street ) {

        this.street = street;
    }
}
