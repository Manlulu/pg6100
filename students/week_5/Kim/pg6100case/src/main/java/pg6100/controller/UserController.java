package pg6100.controller;

import pg6100.ejb.comment.CommentEJB;
import pg6100.ejb.news.NewsEJB;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.Comment;
import pg6100.entity.User;
import pg6100.util.SecurityUtil;

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


    public String createUser() {
        User user = new User(formUsername);

        user = userEJB.createUser(user, formPassword);

        if (user != null) {
            registeredUser = formUsername;
            return "frontPage";
        } else {
            return "userCreate.xhtml";
        }
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
        return "frontPage";
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
        if (user != null) {

            userEJB.deleteUser(user);
        }
        if (deleteMe.getUserName().equalsIgnoreCase(registeredUser)) {
            registeredUser = null;
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
        return isOK;
    }
}
