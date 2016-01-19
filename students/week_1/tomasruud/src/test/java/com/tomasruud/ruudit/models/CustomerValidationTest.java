package com.tomasruud.ruudit.models;

import com.tomasruud.ruudit.generators.StringGenerator;
import com.tomasruud.ruudit.testCases.ValidationBaseCase;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Calendar;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Tomas Ruud
 * @since 18.01.2016
 */
public class CustomerValidationTest extends ValidationBaseCase {

    @Test
    public void nonOptionalFieldsAreNotOptional() throws Exception {

        Customer emptyCustomer = new Customer();

        propertyValidationShouldYieldNErrors( 1, "firstName", emptyCustomer );
        propertyValidationShouldYieldNErrors( 1, "lastName", emptyCustomer );
        propertyValidationShouldYieldNErrors( 1, "birthday", emptyCustomer );
        propertyValidationShouldYieldNErrors( 1, "registered", emptyCustomer );
        propertyValidationShouldYieldNErrors( 1, "address", emptyCustomer );
    }

    @Test
    public void optionalFieldsAreOptional() throws Exception {

        propertyValidationShouldYieldNErrors( 0, "middleName", new Customer() );
    }

    @Test
    public void registeredDateCannotBeInTheFuture() throws Exception {

        Customer customer = new Customer();
        Calendar date = Calendar.getInstance();
        date.add( Calendar.DATE, 1 );

        customer.setRegistered( date.getTime() );

        propertyValidationShouldYieldNErrors( 1, "registered", customer );
    }

    @Test
    public void birthdayCannotBeInTheFuture() throws Exception {

        Customer customer = new Customer();
        Calendar date = Calendar.getInstance();
        date.add( Calendar.DATE, 1 );

        customer.setBirthday( date.getTime() );

        propertyValidationShouldYieldNErrors( 1, "birthday", customer );
    }

    @Test
    public void customersMustBeAtLeastEighteenYearsOld() throws Exception {

        Customer customer = new Customer();
        Calendar date = Calendar.getInstance();
        date.add( Calendar.YEAR, -18 );

        customer.setBirthday( date.getTime() );
        customer.setRegistered( Calendar.getInstance().getTime() );

        Set<ConstraintViolation<Customer>> violations = getValidator().validate( customer );

        boolean birthdayError = false;

        for( ConstraintViolation violation : violations)
            if( violation.getPropertyPath().toString().equalsIgnoreCase( "birthdayField" ) )
                birthdayError = true;

        assertFalse( birthdayError );
    }

    @Test
    public void customersUnderEighteenIsNotAllowed() throws Exception {

        Customer customer = new Customer();
        Calendar date = Calendar.getInstance();
        date.add( Calendar.YEAR, -17 );

        customer.setBirthday( date.getTime() );
        customer.setRegistered( Calendar.getInstance().getTime() );

        Set<ConstraintViolation<Customer>> violations = getValidator().validate( customer );

        System.out.print( violations );

        boolean birthdayError = false;

        for( ConstraintViolation violation : violations)
            if( violation.getPropertyPath().toString().equalsIgnoreCase( "birthday" ) )
                birthdayError = true;

        assertTrue( birthdayError );
    }

    @Test
    public void stringsCantBeMoreThan64Characters() throws Exception {

        Customer customer = new Customer();

        customer.setFirstName( StringGenerator.randomString( 65 ) );
        customer.setMiddleName( StringGenerator.randomString( 65 ) );
        customer.setLastName( StringGenerator.randomString( 65 ) );

        propertyValidationShouldYieldNErrors( 1, "firstName", customer );
        propertyValidationShouldYieldNErrors( 1, "middleName", customer );
        propertyValidationShouldYieldNErrors( 1, "lastName", customer );
    }
}