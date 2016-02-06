package ninja.idar.models;

import ninja.idar.helpers.GenericBeanValidationTestHelper;
import ninja.idar.helpers.UserTestHelper;
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
        user = UserTestHelper.getLegalUser();
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
}