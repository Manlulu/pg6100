package no.ingesen.martin.dto;

import no.ingesen.martin.dto.constraint.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.constraints.Past;
import java.time.LocalDate;

/*
    AA: for names of the queries, you might want to have something like

    public static final ALL_NORWEGIAN_QUERY = "Customer.allNorwegian"

    and then

     @NamedQuery(name = Customer.ALL_NORWEGIAN_QUERY, ...

     this to avoid copy&paste strings in the users of this class, eg see CustomerIT.
     This would had been marked down as a "maintainability" error
 */


@Entity
@NamedQueries({
    @NamedQuery(name ="Customer.allNorwegian", query="SELECT c FROM Customer c WHERE c.address.country = 'Norway'"),
    @NamedQuery(name ="Customer.allFromOslo", query="SELECT c FROM Customer c WHERE c.address.city = 'Oslo'")
})
@SequenceGenerator(name = "CUS_SEQ", initialValue = 50)
@OldEnough
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CUS_SEQ")
    private long customerId;

    @NotNull
    @Length(max=64, message="The name can't be any longer than 64 characters.")
    private String name;

    @Length(max=64, message="The middleName can't be any longer than 64 characters.")
    private String middleName;

    @NotNull
    @Length(max=64, message="The surname can't be any longer than 64 characters.")
    private String surname;

    @NotNull
    @NotInFuture
    private LocalDate dateOfBirth;

    @NotNull
    @NotInFuture
    private LocalDate dateOfRegistration;

    @Valid
    @NotNull
    @Embedded
    private Address address;


    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

