package com.tomasruud.ruudit.security.passwords;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashTest {

    @Test
    public void sameSaltCreateEqualHash() throws Exception {

        String salt = "pepper";
        String text = "something";

        String hash = Hash.generate( text, salt );
        String other = Hash.generate( text, salt );

        assertEquals( hash, other );
    }
}