package ninja.idar.service.userservice;

import ninja.idar.helpers.UserTestHelper;
import ninja.idar.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Idar Vassdal on 06.02.2016.
 */
public class PasswordConverterTest {
    private PasswordConverter passwordConverter;

    @Before
    public void setUp() throws Exception {
        passwordConverter = new PasswordConverter();

    }

    @Test
    public void testGenerateSalt() throws Exception {
        assertNotNull("should generate salt", passwordConverter.generateSalt());
    }

    @Test
    public void testGenerateHash() throws Exception {
        assertNotNull("should generate hash", passwordConverter.generateHash(UserTestHelper.DEFAULT_TEST_PASSWORD, passwordConverter.generateSalt()));
    }

    @Test
    public void testValidatePassword() throws Exception {
        User user = new User();
        String password = UserTestHelper.DEFAULT_TEST_PASSWORD;
        String falsePassword = "some other PassW0rd";

        String salt = passwordConverter.generateSalt();
        String hash = passwordConverter.generateHash(password, salt);
        user.setHash(hash);
        user.setSalt(salt);

        assertTrue("correct password should succeed", passwordConverter.validatePassword(user, password));
        assertFalse("wrong password should fail", passwordConverter.validatePassword(user, falsePassword));
    }
}
