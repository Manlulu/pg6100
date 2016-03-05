package pg6100.ejb.user;


import org.apache.commons.codec.digest.*;
import pg6100.ejb.comment.CommentEJB;
import pg6100.ejb.news.NewsEJB;
import pg6100.entity.Comment;
import pg6100.entity.News;
import pg6100.entity.User;
import pg6100.util.DatabaseConfig;
import pg6100.util.SecurityUtil;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;


@Stateless
public class UserEJB {

    @PersistenceContext(unitName = DatabaseConfig.database_name)
    private EntityManager entityManager;

    @PersistenceContext(unitName = DatabaseConfig.database_name)
    private EntityManager entityManagerNews;

    public UserEJB() {
    }

    public void setEntityManagerNews(EntityManager entityManagerNews){
        this.entityManagerNews = entityManagerNews;
    }

    public UserEJB(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User createUser(User user, String password) {
        if (user == null || password == null || password.isEmpty()) {
            return null;
        }
        if (password.length() > 64) {
            return null;
        }
        if (getUserByUserName(user.getUserName()) != null) {
            return null;
        }

        String hash = getHash(user, password);

        user.setHash(hash);

        try {
            entityManager.persist(user);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public String getHash(User user, String password){
        String salt = getSalt();
        user.setSalt(salt);

        return SecurityUtil.computeHash(password, user.getSalt());
    }

    public User getUserById(long id) {
        try {
            Query findById = entityManager.createNamedQuery(User.query_getUserById, User.class).setParameter("id", id);
            return (User) findById.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> getAllUsers() {
        try {
            Query findById = entityManager.createNamedQuery(User.query_getAllUsers, User.class);
            return findById.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public User getUserByUserName(String userName) {
        try {
            Query findById = entityManager.createNamedQuery(User.query_getUserByUserName, User.class).setParameter("userName", userName);
            return (User) findById.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteUser(User user) {
        try {
            entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User updateUser(User user) {
        entityManager.merge(user);
        return user;
    }


    @NotNull
    protected String getSalt() {
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32; // 2^5
        int n = 26;
        assert n * bitsPerChar >= 128;

        String salt = new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
        return salt;
    }
}
