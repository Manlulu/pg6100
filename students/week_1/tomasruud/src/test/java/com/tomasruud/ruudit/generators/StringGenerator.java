package com.tomasruud.ruudit.generators;

/**
 * @author Tomas Ruud
 * @since 19.01.16
 */
public class StringGenerator {

    public static String randomString( int characters ) {

        String randomString = "";

        for( int i = 0; i < characters; i++ )
            randomString += 'a';

        return randomString;
    }
}
