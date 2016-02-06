package ninja.idar.service.userservice;

import ninja.idar.helpers.GenericBeanIntegrationTestHelper;
import ninja.idar.helpers.StringHelper;
import ninja.idar.helpers.UserTestHelper;
import ninja.idar.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public class UserJpaTest extends GenericBeanIntegrationTestHelper<UserJpa> {
    private UserJpa userJpa;
    private static final int EXISTING_USER_ID = 1001;
    private static final int NON_EXISTING_USER_ID = 654321;

    @Before
    public void setUp() throws Exception {
        userJpa = new UserJpa(getPersister());
    }

    @Test
    public void testGetUsers() throws Exception {
        assertTrue("init.sql should populate users", 0 < userJpa.getAll().size());
    }

    @Test
    public void testFindUser() throws Exception {
        User user = userJpa.getById(NON_EXISTING_USER_ID);
        assertNull("User with id" + NON_EXISTING_USER_ID + " should not exist from init.sql", user);

        user = userJpa.getById(EXISTING_USER_ID);
        assertNotNull("User with id " + EXISTING_USER_ID + " should exist from init.sql", user);

    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = userJpa.getById(EXISTING_USER_ID);
        String newEmail = "testEmail@somemail.com";

        assertNotEquals("User should not have newEmail by default", newEmail, user.getEmail());

        user.setEmail(newEmail);
        userJpa.update(user);

        user = userJpa.getById(EXISTING_USER_ID);
        assertEquals("User should have new email after persisting", newEmail, user.getEmail());

    }

    @Test
    public void testDeleteUserByID() throws Exception {
        assertNotEquals("User should exist before deletion", null, userJpa.getById(EXISTING_USER_ID));
        userJpa.deleteById(EXISTING_USER_ID);
        assertEquals("User should not exist after deletion", null, userJpa.getById(EXISTING_USER_ID));
    }

    @Test
    public void testDeleteUserByObject() throws Exception {
        User user = userJpa.getById(EXISTING_USER_ID);
        assertNotEquals("User should exist before deletion", null, userJpa.getById(user.getId()));
        userJpa.deleteByObject(user);
        assertEquals("User should not exist after deletion", null, userJpa.getById(user.getId()));
    }

    @Test
    public void testPersistUser() throws Exception {
        User u = UserTestHelper.getLegalUser();
        assertFalse("User should not have id prior to persisting", 0 < u.getId());
        userJpa.persist(u);
        assertTrue("User should not have id post persisting", 0 < u.getId());
    }

    @Test
    public void testPersistWithPassword() throws Exception {
        User u = UserTestHelper.getLegalUser();
        String password = UserTestHelper.DEFAULT_TEST_PASSWORD;
        assertFalse("User should not have id prior to persisting", 0 < u.getId());
        userJpa.persist(u, password);
        assertTrue("User should not have id post persisting", 0 < u.getId());
    }

    @Test
    public void testClose() throws Exception {
        assertTrue("Before closing, persister should be open", getPersister().isOpen());
        userJpa.close();
        assertFalse("After closing, persister should not be open", getPersister().isOpen());
    }

}
