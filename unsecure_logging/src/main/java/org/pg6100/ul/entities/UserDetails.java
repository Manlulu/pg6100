package org.pg6100.ul.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user_details_table")
@NamedQuery(name = UserDetails.DELETE_ALL, query = "DELETE FROM UserDetails")
public class UserDetails {

    public static final String DELETE_ALL = "delete_all";


    @Id
    private String userId;

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
