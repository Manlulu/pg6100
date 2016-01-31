package no.ingesen.martin.ejb;

import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class ITBase {
    protected EntityManagerFactory factory;
    protected EntityManager entityManager;

    protected CommentBean commentBean;
    protected PostBean postBean;
    protected UserBean userBean;
    protected VoteBean voteBean;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("SecurityNews");
        entityManager = factory.createEntityManager();
        commentBean = new CommentBean(entityManager);
        postBean = new PostBean(entityManager);
        userBean = new UserBean(entityManager);
        voteBean = new VoteBean(entityManager);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
        factory.close();
    }
}
