package pg6100.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;


public class UserTest {

    private Validator validator;
    private User user;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        user = new User();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidUser() throws Exception {
        user = new User("Valid username");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        printViolations(violations);

        assertEquals(2, violations.size());

        user.setHash("Dummy hash");
        user.setSalt("Dummy salt");

        violations = validator.validate(user);
        printViolations(violations);

        assertEquals(0, violations.size());
    }

    @Test
    public void testUserNameMax64Letters() throws Exception {
        user.setUserName("asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf1");
        user.setHash("Dummy hash");
        user.setSalt("Dummy salt");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        printViolations(violations);

        assertEquals(1, violations.size());
    }


    @Test
    public void testUserNameCanNotBeNull() throws Exception {
        user = new User(null);
        user.setHash("Dummy hash");
        user.setSalt("Dummy salt");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        printViolations(violations);

        assertEquals(1, violations.size());
    }


    private void printViolations(Set<ConstraintViolation<User>> violations) throws Exception {
        System.out.println("Violations: ");
        for (ConstraintViolation<User> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}