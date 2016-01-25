package ninja.idar.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Idar Vassdal on 25.01.2016.
 * regex is
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;

    @NotNull
    private String username;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]+(?:(\\.|_)[A-Za-z0-9!#$%&'*+/=?^`{|}~-]+)*@(?!([a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.[a-zA-Z0-9]*\\.))(?:[A-Za-z0-9](?:[a-zA-Z0-9-]*[A-Za-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?")
    // http://regexr.com/391t1
    private String email;

    @NotNull
    @Pattern(regexp = "(?=[#$-/:-?{-~!\"^_`\\[\\]a-zA-Z]*([0-9#$-/:-?{-~!\"^_`\\[\\]]))(?=[#$-/:-?{-~!\"^_`\\[\\]a-zA-Z0-9]*[a-zA-Z])[#$-/:-?{-~!\"^_`\\[\\]a-zA-Z0-9]{6,}")
    // Source: http://regexr.com/391t1
    // Password must be 6 characters and contain at least 1 digit or special character but no spaces.
    private String password;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
