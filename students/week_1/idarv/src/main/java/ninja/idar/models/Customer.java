package ninja.idar.models;

import ninja.idar.constraints.Age;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(query = "Select e from Customer e where LOWER(e.address.country) LIKE LOWER('Norway')", name = "allNorwegian"),
        @NamedQuery(query = "Select e from Customer e where LOWER(e.address.city) LIKE LOWER('Oslo')", name = "allFromOslo")
})
@Age(min = 18)
public class Customer {
    public static final String ALL_NORWEGIAN = "allNorwegian";
    public static final String ALL_FROM_OSLO = "allFromOslo";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long customerID;

    @NotNull(message = "Customer requires first name")
    @Size(max = 64)
    private String firstName;

    @Size(max = 64)
    private String middleName;

    @NotNull(message = "Customer requires last name")
    @Size(max = 64)
    private String surName;

    @NotNull(message = "Customer requires date of birth (DOB)")
    private Date dateOfBirth;

    @NotNull(message = "Customer requires date of registration (DOR)")
    private Date dateOfRegistration;

    @Valid
    private Address address;

    public Customer() {
    }

    public Customer(String firstName, String middleName, String surName, Date dateOfBirth, Date dateOfRegistration, Address address) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;
        this.address = address;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", surName='" + surName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfRegistration=" + dateOfRegistration +
                ", address=" + address +
                '}';
    }
}
