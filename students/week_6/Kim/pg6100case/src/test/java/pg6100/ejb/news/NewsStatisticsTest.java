package pg6100.ejb.news;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pg6100.ejb.comment.CommentEJB;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.Comment;
import pg6100.entity.News;
import pg6100.entity.User;
import pg6100.entity.enums.Country;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;


public class NewsStatisticsTest {

    private NewsStatistics newsStatistics;
    private News news;
    private NewsEJB newsEJB;
    private CommentEJB commentEJB;
    private EntityManager entityManagerNews;
    private EntityManager entityManagerComment;
    private EntityTransaction entityTransaction;
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);

        entityManagerNews = entityManagerFactory.createEntityManager();
        entityManagerComment = entityManagerFactory.createEntityManager();

        newsEJB = new NewsEJB(entityManagerNews);
        commentEJB = new CommentEJB(entityManagerComment);

        newsStatistics = new NewsStatistics();
        newsStatistics.setNewsEJB(newsEJB);
        news = newsEJB.getNewsById(1);
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
        newsEJB.createNews(news1);
        newsEJB.createNews(news2);
        newsEJB.createNews(news3);
        entityTransaction.commit();

        int numberOfNewsThisDay = newsStatistics.getNumberOfNewsThisDay();

        assertEquals(3, numberOfNewsThisDay);

    }

    @Test
    public void testGetNumberOfCountriesOnOneNews() throws Exception {
        NewsStatistics newsStatistics = new NewsStatistics();
        newsStatistics.setNewsEJB(newsEJB);

        User user1 = new User("User 1");
        User user2 = new User("User 2");
        User user3 = new User("User 3");
        user1.setCountry(Country.BELGIUM);
        user2.setCountry(Country.BELGIUM);
        user3.setCountry(Country.NORWAY);

        Comment comment1 = new Comment("Commenting");
        Comment comment2 = new Comment("Commenting");
        Comment comment3 = new Comment("Commenting");

        EntityManager entityManagerUser = entityManagerFactory.createEntityManager();
        entityTransaction = entityManagerUser.getTransaction();
        UserEJB userEJB = new UserEJB(entityManagerUser);

        entityTransaction.begin();
        userEJB.createUser(user1, "password1");
        userEJB.createUser(user2, "password2");
        userEJB.createUser(user3, "password3");
        entityTransaction.commit();

        entityTransaction = entityManagerComment.getTransaction();
        entityTransaction.begin();
        commentEJB.createComment(comment1);
        commentEJB.createComment(comment2);
        commentEJB.createComment(comment3);
        entityTransaction.commit();

        comment1.setUser(user1);
        comment2.setUser(user2);
        comment3.setUser(user3);


        news.addComment(comment1);
        news.addComment(comment2);
        news.addComment(comment3);

        newsEJB.updateNews(news);

        News updatedNews = newsEJB.getNewsById(1);

        int numberOfCountries = newsStatistics.getNumberOfCountriesOnOneNews(updatedNews);

        assertEquals(2, numberOfCountries);
    }
}