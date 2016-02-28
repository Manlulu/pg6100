package pg6100.entity;


import org.hibernate.validator.constraints.NotBlank;
import pg6100.entity.enums.Country;

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

    public static final String query_getUserById = "User.getUserById";
    public static final String query_getAllUsers = "User.getAllUsers";
    public static final String query_getUserByUserName = "User.getUserByUserName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 64)
    @NotBlank
    private String userName;


    @NotNull
    private String hash;

    @NotNull @Size(max = 26)
    private String salt;



    @Enumerated(EnumType.STRING)
    private Country country = Country.OTHER;

    @OneToMany(mappedBy="user", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE}) // TODO Find a better way. Dont want to delete the Comment.
    private List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(name = "USR_NWS")
    private List<News> news;

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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User other = (User) o;


        return this.getUserName().equalsIgnoreCase(other.getUserName());

    }

}
