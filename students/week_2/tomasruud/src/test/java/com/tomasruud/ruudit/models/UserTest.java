package com.tomasruud.ruudit.models;

import com.tomasruud.ruudit.generators.StringGenerator;
import com.tomasruud.ruudit.cases.ValidationTestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UserTest extends ValidationTestCase {

    private static final String VALID_USERNAME = "J3RRY";

    @Test
    public void usernameMustBePresent() throws Exception {

        User user = new User();
        user.setUsername( null );

        propertyValidationShouldFail( "username", user );

        user.setUsername( VALID_USERNAME );

        propertyValidationShouldNotFail( "username", user );
    }

    @Test
    public void usernameLength() throws Exception {

        User user = new User();
        user.setUsername( StringGenerator.randomString( 65 ) );

        propertyValidationShouldFail( "username", user );

        user.setUsername( StringGenerator.randomString( 64 ) );

        propertyValidationShouldNotFail( "username", user );
    }

    @Test
    public void usernameFormatMustBeValid() throws Exception {

        final User user = new User();

        List<String> validNames = Arrays.asList(
                "tomas",
                "TOMAS",
                "tom_as",
                "to_-mas",
                "t0m145_"
        );

        validNames.forEach( name -> {

            user.setUsername( name );
            propertyValidationShouldNotFail( "username", user );
        } );

        List<String> invalidNames = Arrays.asList(
                "i have space",
                "what?",
                "...",
                "nocomma,",
                "æøå"
        );

        invalidNames.forEach( name -> {

            user.setUsername( name );
            propertyValidationShouldFail( "username", user );
        } );
    }
}