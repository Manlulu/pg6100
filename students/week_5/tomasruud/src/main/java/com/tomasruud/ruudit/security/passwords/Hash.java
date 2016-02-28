package com.tomasruud.ruudit.security.passwords;

import org.apache.commons.codec.digest.DigestUtils;

public class Hash {

    public static String generate( String text, String salt ) {

        if( text == null || salt == null )
            return null;

        String combined = text + salt;

        return DigestUtils.sha256Hex( combined );
    }
}
