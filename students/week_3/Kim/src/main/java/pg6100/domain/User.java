package pg6100.domain;


import pg6100.domain.Enum.Country;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = User.query_getUserById, query = "SELECT u FROM User u WHERE u.userId = :id"),
        @NamedQuery(name = User.query_getAllUsers, query = "SELECT u FROM User u"),
        @NamedQuery(name = User.query_getUserByUserName, query = "SELECT u FROM User u WHERE u.userName = :userName")
})
public class User {

    public static final String query_getUserById = "user.getUserById";
    public static final String query_getAllUsers = "user.getAllUsers";
    public static final String query_getUserByUserName = "user.getUserByUserName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @NotNull
    @Column(unique = true)
    @Size(max = 64)
    private String userName;


    @NotNull
    private String hash;

    @NotNull @Size(max = 26)
    private String salt;



    @Enumerated(EnumType.STRING)
    private Country country = Country.OTHER;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL) // TODO Find a better way. Dont want to delete the comment.
    private List<Comment> comments;

    public User(){
    }

    public User(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
