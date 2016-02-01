package ninja.idar.models;

import ninja.idar.helpers.GenericBeanIntegrationTestHelper;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
public class UserIT extends GenericBeanIntegrationTestHelper<User> {
    private User user;

//    @Before
//    public void setUp() throws Exception {
//        user = new User();
//    }

    @AfterClass
    public static void afterTests(){
        closePersister();
    }

    @Test
    public void testUserTableIsPopulated() throws Exception {
        assertTrue(isTablePopulated("User"));
    }

    @Test
    public void testNamedQueries() throws Exception {
        assertTrue("GET ALL query should return results because init.sql has run",
                getPersister().createNamedQuery(User.USERS_ALL, User.class).getResultList().size() > 0);

    }

    @Test
    public void testPersist() throws Exception {
        user = new User("username", "email@email.com", "password_");
        assertFalse(user.getId() > 0);
        persistEntity(user);
        assertTrue(user.getId() > 0);
    }
}