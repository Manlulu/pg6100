package ninja.idar.models.IntegrationTests;

import ninja.idar.models.Post;
import ninja.idar.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

/**
 * Created by Idar Vassdal on 25.01.2016.
 */
public class PostIntegrationTests {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Post post;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
        post = new Post();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testEmptyPost() throws Exception {
        assertEquals(1, 1);

    }
}