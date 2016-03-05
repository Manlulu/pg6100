package pg6100.ejb.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pg6100.ejb.comment.CommentEJB;
import pg6100.ejb.news.NewsEJB;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.Comment;
import pg6100.entity.News;
import pg6100.entity.User;
import pg6100.entity.embeddable.Address;
import pg6100.entity.enums.Country;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class JpaUserDaoIT {

    private User user;
    private UserEJB userEJB;
    private CommentEJB commentEJB;
    private NewsEJB newsEJB;
    private EntityTransaction entityTransaction;
    private EntityManager entityManager;
    private EntityManager entityManagerComment;
    private EntityManager entityManagerNews;
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);
        entityManager = entityManagerFactory.createEntityManager();
        entityManagerComment = entityManagerFactory.createEntityManager();
        entityManagerNews = entityManagerFactory.createEntityManager();
        userEJB = new UserEJB(entityManager);
        commentEJB = new CommentEJB(entityManagerComment);
        newsEJB = new NewsEJB(entityManagerNews);
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
    public void testCreateUserWithAllFields() throws Exception {
        Address address = new Address("Sogn og Fjordane", "Årdal", "Farnesvegen");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date dateOfBirth = df.parse("19-01-1956");
        Date dateOfRegistration = df.parse("18-01-2016");

        List<Comment> commentList = commentEJB.getAllComment();
        List<News> newsList = newsEJB.getAllNews();

        User user = new User("Test username", "FirstName", "Last name", address, Country.ENGLAND);

        user.setComments(commentList);
        user.setNews(newsList);


        entityTransaction.begin();
        User returned = userEJB.createUser(user, "Test password");
        if (returned != null) {
            entityTransaction.commit();
        }
        assertNotNull(returned);
        assertEquals(returned.getUserName(), "Test username");
        assertEquals(returned.getFirstName(), "FirstName");
        assertEquals(returned.getLastName(), "Last name");
        assertEquals(returned.getAddress().getState(), "Sogn og Fjordane");
        assertEquals(returned.getAddress().getCity(), "Årdal");
        assertEquals(returned.getAddress().getStreet(), "Farnesvegen");
        assertEquals(returned.getCountry(), Country.ENGLAND);
        assertEquals(3, returned.getComments().size());
        assertEquals(3, returned.getNews().size());
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


    @Test
    public void testDeleteUser() throws Exception {
        EntityManager entityManagerNews = entityManagerFactory.createEntityManager();
        userEJB.setEntityManagerNews(entityManagerNews);
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