package pg6100.repository.News;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pg6100.domain.News;
import pg6100.repository.Comment.JpaCommentDao;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;


public class NewsStatisticsTest {

    private NewsStatistics newsStatistics;
    private News news;
    private JpaNewsDao jpaNewsDao;
    private JpaCommentDao jpaCommentDao;
    private EntityManager entityManagerNews;
    private EntityManager entityManagerComment;
    private EntityTransaction entityTransaction;
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);

        entityManagerNews = entityManagerFactory.createEntityManager();
        entityManagerComment = entityManagerFactory.createEntityManager();

        jpaNewsDao = new JpaNewsDao(entityManagerNews);
        jpaCommentDao = new JpaCommentDao(entityManagerComment);

        newsStatistics = new NewsStatistics();
        newsStatistics.setJpaNewsDao(jpaNewsDao);
        news = jpaNewsDao.getNewsById(1);
    }

    @After
    public void tearDown() throws Exception {
        if (entityManagerComment.isOpen())
            entityManagerComment.close();
        if (entityManagerFactory.isOpen())
            entityManagerFactory.close();
        if (entityManagerNews.isOpen())
            entityManagerNews.close();
    }

    @Test
    public void testGetNumberOfTotalNews() throws Exception {
        assertEquals(3, newsStatistics.getNumberOfTotalNews());
    }

    @Test
    public void testGetNumberOfNewsThisDay() throws Exception {
        News news1 = new News("N1", "ASdfg1");
        News news2 = new News("N2", "ASdfg2");
        News news3 = new News("N3", "ASdfg3");

        entityTransaction = entityManagerNews.getTransaction();
        entityTransaction.begin();
        jpaNewsDao.createNews(news1);
        jpaNewsDao.createNews(news2);
        jpaNewsDao.createNews(news3);
        entityTransaction.commit();

        int numberOfNewsThisDay = newsStatistics.getNumberOfNewsThisDay();

        assertEquals(3, numberOfNewsThisDay);

    }

    @Test
    public void testGetNumberOfCountriesOnOneNews() throws Exception {
        NewsStatistics newsStatistics = new NewsStatistics();
        newsStatistics.setJpaNewsDao(jpaNewsDao);

        news.addComment(jpaCommentDao.getCommentById(1));
        news.addComment(jpaCommentDao.getCommentById(2));
        news.addComment(jpaCommentDao.getCommentById(3));

        News updatedNews = jpaNewsDao.getNewsById(1);

        int numberOfCountries = newsStatistics.getNumberOfCountriesOnOneNews(updatedNews);

        assertEquals(2, numberOfCountries);
    }

}