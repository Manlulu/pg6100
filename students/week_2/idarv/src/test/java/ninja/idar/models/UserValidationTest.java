package ninja.idar.models;

import helpers.GenericBeanValidationTestHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
public class UserValidationTest extends GenericBeanValidationTestHelper<User> {
    private User user;

    @Before
    public void setUp() throws Exception {
        initLegalUser();
    }

    @AfterClass
    public static void afterTests() throws Exception {
        closeValidationFactory();
    }

    @Test
    public void testEmptyUser() throws Exception {
        user = new User();
        assertFalse("Empty user should not be valid", isValid(user));
    }

    @Test
    public void testLegalUser() throws Exception {
        assertTrue("Legal user should have no validations", isValid(user));
    }

    @Test
    public void testEmailValidation() throws Exception {
        user.setEmail(null);
        assertFalse("User email cannot be null", isValidProperty(user, "email"));

        user.setEmail("email");
        assertFalse("User email must follow email pattern", isValidProperty(user, "email"));

        user.setEmail("testmail@mail.com");
        assertTrue("testmail@mailm.com should pass as a legal email", isValidProperty(user, "email"));
    }

    @Test
    public void testPasswordValidation() throws Exception {

        user.setPassword(null);
        assertFalse("User email cannot be null", isValidProperty(user, "password"));

        user.setPassword("pass1");
        assertFalse("Password must be at least 6 characters long", isValidProperty(user, "password"));

        user.setPassword("password");
        assertFalse("Password must have a symbol or number", isValidProperty(user, "password"));

        user.setPassword("password1");
        assertTrue("password1 should pass as a password", isValidProperty(user, "password"));

        user.setPassword("password_");
        assertTrue("password_ should pass as a password", isValidProperty(user, "password"));
    }


    private void initLegalUser() {
        user = new User("username", "email@email.com", "password_");
    }
}