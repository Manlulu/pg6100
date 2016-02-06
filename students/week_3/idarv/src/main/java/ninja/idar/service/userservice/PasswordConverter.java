package ninja.idar.service.userservice;

import ninja.idar.models.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * Created by Idar Vassdal on 06.02.2016.
 */
public class PasswordConverter {

    /**
     * src: https://github.com/arcuri82/pg6100/blob/master/security/src/main/java/org/pg6100/security/ejb/UserEJB.java
     *
     * @return String salt
     */
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32; // 2^5
        int n = 26;
        assert n * bitsPerChar >= 128;

        String salt = new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
        return salt;
    }

    public String generateHash(String password, String salt){

        String combined = password + salt;
        return DigestUtils.sha256Hex(combined);
    }

    public boolean validatePassword(User user, String incPassword) {
        String hash =generateHash(incPassword, user.getSalt());
        return Objects.equals(hash, user.getHash());
    }
}
