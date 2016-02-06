package ninja.idar.models;

import ninja.idar.helpers.GenericBeanIntegrationTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class CommentIT extends GenericBeanIntegrationTestHelper<Comment> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager persister;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        persister = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        persister.close();
        entityManagerFactory.close();
    }

    @Test
    public void testLoadsInitScript() throws Exception {
        assertTrue("init.sql should populate comments in the database", isTablePopulated("Comment"));
    }

    @Test
    public void testPostNamedQueries() throws Exception {
        assertTrue("GET ALL query should return results because init.sql has run",
                getPersister().createNamedQuery(Comment.COMMENT_ALL).getResultList().size() > 0);
    }
}
