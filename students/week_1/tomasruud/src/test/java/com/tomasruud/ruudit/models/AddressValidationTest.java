package com.tomasruud.ruudit.models;

import com.tomasruud.ruudit.generators.StringGenerator;
import com.tomasruud.ruudit.testCases.ValidationBaseCase;
import org.junit.Test;

/**
 * @author Tomas Ruud
 * @since 18.01.16
 */
public class AddressValidationTest extends ValidationBaseCase {

    @Test
    public void optionalFieldsAreOptional() throws Exception {

        propertyValidationShouldYieldNErrors( 0, "state", new Address() );
    }

    @Test
    public void nonOptionalFieldsAreNotOptional() throws Exception {

        Address emptyAddress = new Address();

        propertyValidationShouldYieldNErrors( 1, "city", emptyAddress );
        propertyValidationShouldYieldNErrors( 1, "street", emptyAddress );
    }

    @Test
    public void stringsCantBeMoreThan64Characters() throws Exception {

        Address address = new Address();

        address.setCity( StringGenerator.randomString( 65 ) );
        address.setState( StringGenerator.randomString( 65 ) );
        address.setStreet( StringGenerator.randomString( 65 ) );

        propertyValidationShouldYieldNErrors( 1, "city", address );
        propertyValidationShouldYieldNErrors( 1, "state", address );
        propertyValidationShouldYieldNErrors( 1, "street", address );
    }
}