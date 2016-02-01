package com.tomasruud.ruudit.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    @Size( max = 64 )
    @Pattern( regexp = "^[A-Za-z0-9_-]+$" )
    @Column( unique = true )
    private String username;

    public Long getId() {

        return id;
    }

    public void setId( Long id ) {

        this.id = id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername( String username ) {

        this.username = username;
    }

    @Override
    public boolean equals( Object o ) {

        if( this == o ) return true;
        if( !( o instanceof User ) ) return false;

        User user = ( User ) o;

        return getUsername() != null ? getUsername().equals( user.getUsername() ) :
                user.getUsername() == null;
    }

    @Override
    public int hashCode() {

        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + ( getUsername() != null ? getUsername().hashCode() : 0 );
        return result;
    }
}
