package pg6100.repository.User;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.*;
import pg6100.domain.User;
import pg6100.util.DatabaseConfig;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;



@Stateless

public class JpaUserDao {

    @PersistenceContext(unitName = DatabaseConfig.database_name)
    private EntityManager entityManager;

    public JpaUserDao() {
    }

    public JpaUserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User createUser(User user, String password) {
        if (user == null || password == null || password.isEmpty()) {
            return null;
        }
        if(password.length() > 64){
            return null;
        }

        String salt = getSalt();
        user.setSalt(salt);

        String hash = computeHash(password, user.getSalt());
        user.setHash(hash);

        entityManager.persist(user);

        return user;
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

    @NotNull
    protected String computeHash(String password, String salt) {

        /*
            Given an hash function "f", we have

            f(password) = hash

            the main point is that, even if one knows the details of "f" and has the hash,
            then it is extremely difficult to derive the input password, ie find a function "g"
            such that

            g(hash) = password
         */

        String combined = password + salt;

        /*
            The password is combined with a "salt" to avoid:

            1) two users with same password having same hash. Even if one password gets
               compromised, an attacker would have no way to know if any other
               user has the same password
            2) make nearly impossible a brute force attack based on
               "rainbow tables", ie pre-computed values of all hashes from
               password strings up to length N.
               This is because now the hashed string
               will be at least the length of the salt (eg 26) regardless of
               the length of the password.
         */

        String hash = DigestUtils.sha256Hex(combined);

        return hash;
    }

}
