package com.tomasruud.ruudit.security.passwords;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SaltTest {

    @Test
    public void generatesAValidSalt() throws Exception {

        String salt = Salt.generate();

        assertEquals( 26, salt.length() );
    }
}