package pg6100.controller;

import pg6100.ejb.comment.CommentEJB;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.User;
import pg6100.entity.embeddable.Address;
import pg6100.util.SecurityUtil;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UserController implements Serializable {

    @Inject
    private UserEJB userEJB;

    @EJB
    private CommentEJB commentEJB;

    private String registeredUser;

    private String formUsername;
    private String formPassword;
    private String formPasswordConfirm;
    private String firstName;
    private String lastName;
    private String state, city, street;

    private Long userId;

    private User user;

    @PostConstruct
    public void init() {
        user = new User();
        Address address = new Address();
        user.setAddress(address);
    }


    public String createUser() {
        if (confirmPassword(formPassword, formPasswordConfirm)) {

            user = new User(formUsername);
            if (firstName != null)
                user.setFirstName(firstName);
            if (lastName != null)
                user.setLastName(lastName);
            Address address = new Address();
            if (state != null) {
                address.setState(state);
            }
            if (city != null) {
                address.setCity(city);
            }
            if (street != null) {
                address.setStreet(street);
            }
            user.setAddress(address);

            user = userEJB.createUser(user, formPassword);
            formPassword = null;
            formPasswordConfirm = null;
            if (user != null) {
                registeredUser = formUsername;
                return "frontPage";
            }
        }
        return "userCreate.xhtml";
    }


    public void initUser() {
        user = userEJB.getUserById(userId);
    }

    public String loginUser() {
        boolean loggedIn = login(formUsername, formPassword);
        if (loggedIn) {
            registeredUser = formUsername;
            return "frontPage";
        } else {
            return "userLogin.xhtml";
        }
    }

    public String logoutUser() {
        registeredUser = null;
        user = new User();
        return "frontPage";
    }

    public String updateUser() {
        if (confirmPassword(formPassword, formPasswordConfirm)) {
            String hash = userEJB.getHash(user, formPassword);
            user.setHash(hash);
        }

        // TODO - Cant use blank String to remove address properties
        if (user.getAddress() == null)
            user.setAddress(new Address());

        if (state != null) {
            user.getAddress().setState(state);
        }
        if (city != null) {
            user.getAddress().setCity(city);
        }
        if (street != null) {
            user.getAddress().setStreet(street);
        }

        userEJB.updateUser(user);

        registeredUser = user.getUserName();

        formPassword = null;
        formPasswordConfirm = null;
        return "userDetails.jsf";
    }

    private boolean confirmPassword(String formPassword, String formPasswordConfirm) {
        return formPassword != null && formPasswordConfirm != null && formPassword.equals(formPasswordConfirm);
    }

    public List<User> getAllUsers() {
        return userEJB.getAllUsers();
    }

    public int getNumberOfUsers() {
        return getAllUsers().size();
    }

    public boolean isLoggedIn() {
        return registeredUser != null;
    }


    public String deleteUser(User deleteMe) {
        User user = userEJB.getUserByUserName(deleteMe.getUserName());
        try {
            if (user != null) {
                userEJB.deleteUser(user);
            }
            if (deleteMe.getUserName().equalsIgnoreCase(registeredUser)) {
                registeredUser = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "userList.xhtml";
    }

    public String getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(String registeredUser) {
        this.registeredUser = registeredUser;
    }

    public String getFormUsername() {
        return formUsername;
    }

    public void setFormUsername(String formUsername) {
        this.formUsername = formUsername;
    }

    public String getFormPassword() {
        return formPassword;
    }

    public void setFormPassword(String formPassword) {
        this.formPassword = formPassword;
    }

    public String getFormPasswordConfirm() {
        return formPasswordConfirm;
    }

    public void setFormPasswordConfirm(String formPasswordConfirm) {
        this.formPasswordConfirm = formPasswordConfirm;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private boolean login(String userId, String password) {
        if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        User userDetails = userEJB.getUserByUserName(userId);
        if (userDetails == null) {
            return false;
        }

        String hash = SecurityUtil.computeHash(password, userDetails.getSalt());

        boolean isOK = hash.equals(userDetails.getHash());
        if (isOK)
            user = userDetails;
        return isOK;
    }
}
