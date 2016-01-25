package ninja.idar.models.IntegrationTests;

import ninja.idar.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
public class UserIntegrationTests {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private User user;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
        user = new User();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testEmptyUser() throws Exception {


    }
}