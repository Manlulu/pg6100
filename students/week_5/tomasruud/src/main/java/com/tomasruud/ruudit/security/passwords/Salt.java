package com.tomasruud.ruudit.security.passwords;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Salt {

    public static String generate() {

        SecureRandom random = new SecureRandom();

        int bitsPerChar = 5;
        int twoPowerOfBits = 32;
        int n = 26;

        assert n * bitsPerChar >= 128;

        return new BigInteger( n * bitsPerChar, random ).toString( twoPowerOfBits );
    }
}
