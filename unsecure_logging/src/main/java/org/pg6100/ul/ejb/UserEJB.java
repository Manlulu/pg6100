package org.pg6100.ul.ejb;


import org.apache.commons.codec.digest.DigestUtils;
import org.pg6100.ul.entities.UserDetails;


import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;


@Stateless
public class UserEJB implements Serializable{

    @PersistenceContext(name = "DefaultUnit")
    private EntityManager em;

    public UserEJB(){
    }

    /**
     *
     * @param userId
     * @param password
     * @return {@code false} if for any reason it was not possible to create the user
     */
    public boolean createUser(String userId, String password) {
        if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        UserDetails userDetails = getUser(userId);
        if (userDetails != null) {
            //a user with same id already exists
            return false;
        }

        userDetails = new UserDetails();
        userDetails.setUserId(userId);

        //create a "strong" random string of at least 128 bits, needed for the "salt"
        String salt = getSalt();
        userDetails.setSalt(salt);

        String hash = computeHash(password, salt);
        userDetails.setHash(hash);

        em.persist(userDetails);

        return true;
    }


    /**
     *
     * @param userId
     * @param password
     * @return  {@code true} if a user with the given password exists
     */
    public boolean login(String userId, String password) {
        if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        UserDetails userDetails = getUser(userId);
        if (userDetails == null) {
            return false;
        }

        String hash = computeHash(password, userDetails.getSalt());

        boolean isOK = hash.equals(userDetails.getHash());
        return isOK;
    }


    public UserDetails getUser(String userId){
        return em.find(UserDetails.class, userId);
    }

    public int deleteAllUsers() {
        return em.createNamedQuery(UserDetails.DELETE_ALL).executeUpdate();
    }

    //------------------------------------------------------------------------


    @NotNull
    protected String computeHash(String password, String salt){
        String combined = password + salt;
        String hash = DigestUtils.sha256Hex(combined);
        return hash;
    }

    @NotNull
    protected String getSalt(){
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32; // 2^5
        int n = 26;
        assert n * bitsPerChar >= 128;

        String salt = new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
        return salt;
    }
}
