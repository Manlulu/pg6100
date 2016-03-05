package pg6100.entity;


import org.hibernate.validator.constraints.NotBlank;
import pg6100.entity.embeddable.Address;
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

    @Size(min = 0, max = 32)
    private String firstName;
    @Size(min = 0, max = 32)
    private String lastName;

    @Embedded
    private Address address;


    @NotNull
    private String hash;

    @NotNull @Size(max = 26)
    private String salt;



    @Enumerated(EnumType.STRING)
    private Country country = Country.OTHER;

    @OneToMany(mappedBy="user", cascade = {CascadeType.MERGE, CascadeType.REMOVE}) // TODO Find a better way. Dont want to delete the Comment.
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<News> news;

    public User(){
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String firstName, String lastName, Address address, Country country) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.country = country;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User other = (User) o;


        return this.getUserName().equalsIgnoreCase(other.getUserName());

    }

}
