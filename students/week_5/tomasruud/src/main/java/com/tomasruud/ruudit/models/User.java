package com.tomasruud.ruudit.models;

import com.tomasruud.ruudit.security.passwords.Hash;
import com.tomasruud.ruudit.security.passwords.Salt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@NamedQueries( {
        @NamedQuery( name = User.QUERY_BY_NAME,
                query = "SELECT u FROM User u WHERE u.username = ?" )
} )
public class User {

    public static final String QUERY_BY_NAME = "User.findByUsername";

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull( message = "You must specify a username" )
    @Size( max = 64, message = "Username can't be more than 64 characters" )
    @Pattern( regexp = "^[A-Za-z0-9_-]+$", message = "Username can only contain letters (a-z), numbers, _ and -" )
    @Column( unique = true )
    private String username;

    @Transient
    private String password;

    @NotNull
    private String passwordHash;

    @NotNull
    @Size( max = 50 )
    private String salt;

    @PrePersist
    @PreUpdate
    public void generatePasswordHashHook() {

        // If password is set, assume we want to update/persist the entity
        if( getPassword() == null )
            return;

        // Update the salt every time the password changes
        salt = Salt.generate();
        passwordHash = Hash.generate( getPassword(), getSalt() );
        password = null;
    }

    public boolean comparePassword( String password ) {

        return passwordHash.equals( Hash.generate( password, getSalt() ) );
    }

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

        if( getId() != null ? !getId().equals( user.getId() ) : user.getId() != null )
            return false;

        return getUsername() != null ? getUsername().equals( user.getUsername() ) :
                user.getUsername() == null;
    }

    @Override
    public int hashCode() {

        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + ( getUsername() != null ? getUsername().hashCode() : 0 );
        return result;
    }

    public String getPasswordHash() {

        return passwordHash;
    }

    public String getSalt() {

        return salt;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword( String password ) {

        this.password = password;
    }

    @Override
    public String toString() {

        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
