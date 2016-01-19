package com.tomasruud.ruudit.testCases;

import org.junit.After;
import org.junit.Before;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Tomas Ruud
 * @since 18.01.2016
 */
public abstract class ValidationBaseCase {

    private Validator validator;
    private ValidatorFactory factory;

    @Before
    public void initializeValidator() throws Exception {

        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @After
    public void closeValidator() throws Exception {

        factory.close();
    }

    public Validator getValidator() {

        return validator;
    }

    public <T> Set<ConstraintViolation<T>> propertyValidationShouldYieldNErrors( int errors,
                                                                                 String property,
                                                                                 T object ) {

        Set<ConstraintViolation<T>> violations =
                getValidator().validateProperty( object, property );

        assertEquals( errors, violations.size() );

        return violations;
    }
}
