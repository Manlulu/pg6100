package com.tomasruud.ruudit.models;

import com.tomasruud.ruudit.constraints.Age;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/*
    AA: for names of the queries, you might want to have something like

    public static final ALL_NORWEGIAN_QUERY = "Customer.allNorwegian"

    and then

     @NamedQuery(name = Customer.ALL_NORWEGIAN_QUERY, ...

     this to avoid copy&paste strings in the users of this class, eg see CustomerIT.
     This would had been marked down as a "maintainability" error
 */

/**
 * @author Tomas Ruud
 * @since 18.01.2016
 */
@Entity
@NamedQueries( {
        /*
            AA: "c.address.state = null" is incorrect, as using "null" as default for "Norway"
            makes the code very brittle
         */
        @NamedQuery( name = "allNorwegian", query = "SELECT c FROM Customer c WHERE c.address.state = null" ),
        @NamedQuery( name = "allFromOslo", query = "SELECT c FROM Customer c WHERE c.address.city = 'Oslo'" )
} )
@Age( min = 18 )
public class Customer {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    @Size( max = 64 )
    private String firstName;

    @Size( max = 64 )
    private String middleName;

    @NotNull
    @Size( max = 64 )
    private String lastName;

    @NotNull
    @Temporal( TemporalType.DATE )
    @Past
    private Date birthday;

    @NotNull
    @Temporal( TemporalType.TIMESTAMP )
    @Past
    private Date registered;

    @NotNull
    @Embedded
    @Valid
    private Address address;

    public Long getId() {

        return id;
    }

    public void setId( Long id ) {

        this.id = id;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName( String firstName ) {

        this.firstName = firstName;
    }

    public String getMiddleName() {

        return middleName;
    }

    public void setMiddleName( String middleName ) {

        this.middleName = middleName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName( String lastName ) {

        this.lastName = lastName;
    }

    public Date getBirthday() {

        return birthday;
    }

    public void setBirthday( Date birthday ) {

        this.birthday = birthday;
    }

    public Date getRegistered() {

        return registered;
    }

    public void setRegistered( Date registered ) {

        this.registered = registered;
    }

    public Address getAddress() {

        return address;
    }

    public void setAddress( Address address ) {

        this.address = address;
    }
}
