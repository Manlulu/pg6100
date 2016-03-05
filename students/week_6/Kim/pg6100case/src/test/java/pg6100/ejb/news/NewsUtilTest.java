package pg6100.ejb.news;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.News;
import pg6100.entity.User;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;


public class NewsUtilTest {

    private News news;
    private NewsUtil newsUtil;

    private User user1;
    private User user2;
    private User user3;

    private NewsEJB newsEJB;
    private UserEJB userEJB;


    private EntityManager entityManagerUser;
    private EntityManager entityManagerNews;

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);
        entityManagerUser = entityManagerFactory.createEntityManager();
        entityManagerNews = entityManagerFactory.createEntityManager();

        userEJB = new UserEJB(entityManagerUser);
        newsEJB = new NewsEJB(entityManagerNews);

        newsUtil = new NewsUtil();
        newsUtil.setNewsEJB(newsEJB);

        user1 = userEJB.getUserById(1);
        user2 = userEJB.getUserById(2);
        user3 = userEJB.getUserById(3);

        news = newsEJB.getNewsById(1);

    }

    @After
    public void tearDown() throws Exception {
        if (entityManagerFactory.isOpen())
            entityManagerFactory.close();
        if (entityManagerNews.isOpen())
            entityManagerNews.close();
        if (entityManagerUser.isOpen())
            entityManagerUser.close();
    }

    @Test
    public void testUpVote() throws Exception {
        newsUtil.upVote(user1, news);
        News updatedNews = newsEJB.getNewsById(1);
        assertEquals(1, updatedNews.getVotes());

        newsUtil.upVote(user1, news);
        updatedNews = newsEJB.getNewsById(1);
        assertEquals(0, updatedNews.getVotes());

        newsUtil.upVote(user2, news);
        updatedNews = newsEJB.getNewsById(1);
        assertEquals(1, updatedNews.getVotes());

        newsUtil.upVote(user3, news);
        updatedNews = newsEJB.getNewsById(1);
        assertEquals(2, updatedNews.getVotes());
    }

    @Test
    public void testDownVote() throws Exception {

        newsUtil.downVote(user1, news);
        News updatedNews = newsEJB.getNewsById(1);
        assertEquals(-1, updatedNews.getVotes());

        newsUtil.downVote(user1, news);
        updatedNews = newsEJB.getNewsById(1);
        assertEquals(0, updatedNews.getVotes());

        newsUtil.downVote(user2, news);
        updatedNews = newsEJB.getNewsById(1);
        assertEquals(-1, updatedNews.getVotes());

        newsUtil.downVote(user3, news);
        updatedNews = newsEJB.getNewsById(1);
        assertEquals(-2, updatedNews.getVotes());
    }

    @Test
    public void testUpAndDownVotes() throws Exception {
        newsUtil.upVote(user1, news);
        newsUtil.downVote(user1, news);

        News updatedNews = newsEJB.getNewsById(1);
        assertEquals(-1, updatedNews.getVotes());
    }

    @Test
    public void testDownAndUpVotes() throws Exception {
        newsUtil.downVote(user1, news);
        newsUtil.upVote(user1, news);

        News updatedNews = newsEJB.getNewsById(1);
        assertEquals(1, updatedNews.getVotes());
    }

    @Test
    public void testGetVotedNews() throws Exception {
        News news = newsEJB.getNewsById(1);
        User user1 = userEJB.getUserById(1);
        User user2 = userEJB.getUserById(2);
        User user3 = userEJB.getUserById(3);

        NewsUtil newsUtil = new NewsUtil();
        newsUtil.setNewsEJB(newsEJB);

        newsUtil.upVote(user1, news);
        newsUtil.upVote(user2, news);
        newsUtil.upVote(user3, news);
        newsUtil.upVote(user2, news);

        News newNews = newsEJB.getNewsById(1);
        assertEquals(2, newNews.getVotes());
    }


}