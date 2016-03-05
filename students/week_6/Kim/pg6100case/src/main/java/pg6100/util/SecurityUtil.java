package pg6100.util;


import org.apache.commons.codec.digest.DigestUtils;

import javax.validation.constraints.NotNull;

public class SecurityUtil {

    @NotNull
    public static String computeHash(String password, String salt) {
        String combined = password + salt;
        String hash = DigestUtils.sha256Hex(combined);
        return hash;
    }

}
