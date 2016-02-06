package ninja.idar.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Idar Vassdal on 25.01.2016.
 * regex is
 */
@Entity
@NamedQueries({
        @NamedQuery(query = "Select e from User e", name = User.USERS_ALL),
})
public class User {
    public static final String USERS_ALL = "allUsers";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;

    @NotNull
    private String username;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]+(?:(\\.|_)[A-Za-z0-9!#$%&'*+/=?^`{|}~-]+)*@(?!([a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.))(?:[A-Za-z0-9](?:[a-zA-Z0-9-]*[A-Za-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?")
    // http://regexr.com/391t1
    private String email;

//    @NotNull
//    @Pattern(regexp = "(?=[#$-/:-?{-~!\"^_`\\[\\]a-zA-Z]*([0-9#$-/:-?{-~!\"^_`\\[\\]]))(?=[#$-/:-?{-~!\"^_`\\[\\]a-zA-Z0-9]*[a-zA-Z])[#$-/:-?{-~!\"^_`\\[\\]a-zA-Z0-9]{6,}")
//    // Source: http://regexr.com/391t1
//    // Password must be 6 characters and contain at least 1 digit or special character but no spaces.
//    private String password;

    @NotNull
    private String hash;

    @NotNull
    private String salt;

    public User() {
    }

    public User(String username, String email, String hash, String salt) {
        this.username = username;
        this.email = email;
        this.hash = hash;
        this.salt = salt;
        setId(0);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
