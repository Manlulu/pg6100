package pg6100.repository.News;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pg6100.domain.Comment;
import pg6100.domain.News;
import pg6100.domain.User;
import pg6100.repository.Comment.JpaCommentDao;
import pg6100.repository.User.JpaUserDao;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class NewsUtilTest {

    private News news;
    private NewsUtil newsUtil;

    private User user1;
    private User user2;
    private User user3;

    private JpaNewsDao jpaNewsDao;
    private JpaUserDao jpaUserDao;


    private EntityManager entityManagerUser;
    private EntityManager entityManagerNews;

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);
        entityManagerUser = entityManagerFactory.createEntityManager();
        entityManagerNews = entityManagerFactory.createEntityManager();

        jpaUserDao = new JpaUserDao(entityManagerUser);
        jpaNewsDao = new JpaNewsDao(entityManagerNews);

        newsUtil = new NewsUtil();
        newsUtil.setJpaNewsDao(jpaNewsDao);

        user1 = jpaUserDao.getUserById(1);
        user2 = jpaUserDao.getUserById(2);
        user3 = jpaUserDao.getUserById(3);

        news = jpaNewsDao.getNewsById(1);

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
        newsUtil.upVote(user1, news);

        newsUtil.upVote(user2, news);
        newsUtil.upVote(user3, news);

        News updatedNews = jpaNewsDao.getNewsById(1);
        assertEquals(2, updatedNews.getVotes());
    }

    @Test
    public void testDownVote() throws Exception {
        newsUtil.downVote(user1, news);
        newsUtil.downVote(user1, news);

        newsUtil.downVote(user2, news);
        newsUtil.downVote(user3, news);

        News updatedNews = jpaNewsDao.getNewsById(1);
        assertEquals(-2, updatedNews.getVotes());
    }

    @Test
    public void testUpAndDownVotes() throws Exception {
        newsUtil.upVote(user1, news);
        newsUtil.downVote(user1, news);

        News updatedNews = jpaNewsDao.getNewsById(1);
        assertEquals(-1, updatedNews.getVotes());
    }

    @Test
    public void testDownAndUpVotes() throws Exception {
        newsUtil.downVote(user1, news);
        newsUtil.upVote(user1, news);

        News updatedNews = jpaNewsDao.getNewsById(1);
        assertEquals(1, updatedNews.getVotes());
    }

    @Test
    public void testGetVotedNews() throws Exception {
        News news = jpaNewsDao.getNewsById(1);
        User user1 = jpaUserDao.getUserById(1);
        User user2 = jpaUserDao.getUserById(2);
        User user3 = jpaUserDao.getUserById(3);

        NewsUtil newsUtil = new NewsUtil();
        newsUtil.setJpaNewsDao(jpaNewsDao);

        newsUtil.upVote(user1, news);
        newsUtil.upVote(user2, news);
        newsUtil.upVote(user3, news);
        newsUtil.upVote(user2, news);

        News newNews = jpaNewsDao.getNewsById(1);
        assertEquals(2, newNews.getVotes());
    }


}