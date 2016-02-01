package org.pg6100.security.data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name = "user_details_table")
@NamedQuery(name = UserDetails.DELETE_ALL, query = "DELETE FROM UserDetails")
public class UserDetails {

    public static final String DELETE_ALL = "delete_all";


    @Id
    private String userId;

    /**
     * NEVER store a password in plain text in a database, or anywhere.
     * Rather use an hash combined with a "salt"
     */
    @NotNull
    private String hash;

    @NotNull @Size(max = 26)
    private String salt;


    public UserDetails(){}


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
