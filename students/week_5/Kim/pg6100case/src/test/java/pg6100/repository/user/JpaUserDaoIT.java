package pg6100.repository.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.User;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

public class JpaUserDaoIT {

    private User user;
    private UserEJB userEJB;
    private EntityTransaction entityTransaction;
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);
        entityManager = entityManagerFactory.createEntityManager();
        userEJB = new UserEJB(entityManager);
        entityTransaction = entityManager.getTransaction();

        user = userEJB.getUserById(1);
    }

    @After
    public void tearDown() throws Exception {
        if (entityManagerFactory.isOpen())
            entityManagerFactory.close();
        if (entityManager.isOpen())
            entityManager.close();
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("brukernavn");
        entityTransaction.begin();
        User returned = userEJB.createUser(user, "password");
        entityTransaction.commit();
        assertEquals(4, returned.getUserId());
    }

    @Test
    public void testSecurePassword() throws Exception {
        User user1 = new User("User 1");
        User user2 = new User("User 2");

        User user1return = userEJB.createUser(user1, "Password");
        User user2return = userEJB.createUser(user2, "Password");

        assertNotEquals(user1return.getHash(), user2return.getHash());
        assertNotEquals(user1return.getSalt(), user2return.getSalt());

    }

    @Test
    public void testPasswordMax64Letters() throws Exception {
        String validName = "Valid username";
        user.setUserName(validName);

        String password = "asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf1";

        // This needs to rollback.
        entityTransaction.begin();
        userEJB.createUser(user, password);
        entityTransaction.commit();


        User user = userEJB.getUserByUserName(validName);
        assertNotNull(user);
    }

    @Test
    public void testPasswordMoreThan65LetterFails() throws Exception {
        String validName = "Valid username";
        user.setUserName(validName);

        String password = "asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf11";
        userEJB.createUser(user, password);

        User user = userEJB.getUserByUserName(validName);
        assertNull(user);
    }

    @Test
    public void testGetUserById() throws Exception {
        assertEquals("testUser1", user.getUserName());

        System.out.println("Name: " + user.getUserName());
        System.out.println("County: " + user.getCountry().toString());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> allUsers = userEJB.getAllUsers();
        assertEquals(3, allUsers.size());
    }

    @Test
    public void testGetUserByUserName() throws Exception {
        User user = userEJB.getUserByUserName("testUser1");
        assertEquals(1, user.getUserId());
    }

    @Ignore
    @Test
    public void testDeleteUser() throws Exception {
        entityTransaction.begin();
        userEJB.deleteUser(user);
        entityTransaction.commit();

        User user2 = userEJB.getUserById(1);
        assertNull(user2);
    }

    @Test
    public void testUpdateUser() throws Exception {
        user.setUserName("updatedUserName");
        User returnedUser = userEJB.updateUser(user);
        User updatedUser = userEJB.getUserById(1);

        assertEquals(returnedUser.getUserName(), "updatedUserName");
        assertEquals(updatedUser.getUserName(), "updatedUserName");
    }
}