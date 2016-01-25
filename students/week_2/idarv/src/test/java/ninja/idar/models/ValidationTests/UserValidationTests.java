package ninja.idar.models.ValidationTests;

import ninja.idar.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
public class UserValidationTests {
    private ValidatorFactory validatorFactory;
    private Validator validator;
    private User user;

    @Before
    public void setUp() throws Exception {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        initLegalUser();
    }

    @After
    public void tearDown() throws Exception {
        validatorFactory.close();
    }

    @Test
    public void testEmptyUser() throws Exception {
        user = new User();
        Set<ConstraintViolation<User>> validations = validator.validate(user);
        assertEquals("empty user should have 3 validations", 3, validations.size());
    }

    @Test
    public void testLegalUser() throws Exception {
        Set<ConstraintViolation<User>> validations = validator.validate(user);
        assertEquals("Legal user should have no validations", 0, validations.size());
    }

    @Test
    public void testEmailValidation() throws Exception {
        Set<ConstraintViolation<User>> validations;

        user.setEmail(null);
        validations = validator.validate(user);
        assertEquals("User email cannot be null", 1, validations.size());

        user.setEmail("email");
        validations = validator.validate(user);
        assertEquals("User email must follow email pattern", 1, validations.size());

        user.setEmail("testmail@mail.com");
        validations = validator.validate(user);
        assertEquals("testmail@mailm.com should pass as a legal email", 0, validations.size());
    }

    @Test
    public void testPasswordValidation() throws Exception {
        Set<ConstraintViolation<User>> validations;

        user.setPassword(null);
        validations = validator.validate(user);
        assertEquals("User email cannot be null", 1, validations.size());

        user.setPassword("pass1");
        validations = validator.validate(user);
        assertEquals("Password must be at least 6 characters long", 1, validations.size());

        user.setPassword("password");
        validations = validator.validate(user);
        assertEquals("Password must have a symbol or number", 1, validations.size());

        user.setPassword("password1");
        validations = validator.validate(user);
        assertEquals("password1 should pass as a password", 0, validations.size());

        user.setPassword("password_");
        validations = validator.validate(user);
        assertEquals("password_ should pass as a password", 0, validations.size());
    }



    private void initLegalUser(){
        user = new User("username", "email@email.com", "password_");
    }
}