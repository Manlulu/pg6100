package org.pg6100.ejb.datalayer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class NewsTest {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmbeddedDB");
    protected EntityManager em;
    protected EntityTransaction tx;

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @Before
    public void initEntityManager() throws Exception {
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @After
    public void closeEntityManager() throws SQLException {
        if (em != null) em.close();
    }


    @Test
    public void testBase(){

        assertEquals(0, em.createNamedQuery(News.GET_ALL).getResultList().size());

        tx.begin();
        Comment comment = new Comment(null, "a comment", "a commentator");
        em.persist(comment);

        News news = new News(null, "some text", "an author");
        news.getComments().add(comment);
        em.persist(news);

        tx.commit();

        List<News> res = em.createNamedQuery(News.GET_ALL).getResultList();
        assertEquals(1, res.size());

        assertEquals(1, res.get(0).getComments().size());
    }
}