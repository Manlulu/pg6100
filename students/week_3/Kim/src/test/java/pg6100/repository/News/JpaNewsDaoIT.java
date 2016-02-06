package pg6100.repository.News;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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


public class JpaNewsDaoIT {

    private News news;

    private JpaNewsDao jpaNewsDao;
    private JpaCommentDao jpaCommentDao;
    private JpaUserDao jpaUserDao;

    private EntityTransaction entityTransaction;

    private EntityManager entityManagerNews;
    private EntityManager entityManagerComment;
    private EntityManager entityManagerUser;

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);
        entityManagerNews = entityManagerFactory.createEntityManager();
        entityManagerComment = entityManagerFactory.createEntityManager();
        entityManagerUser = entityManagerFactory.createEntityManager();

        jpaNewsDao = new JpaNewsDao(entityManagerNews);
        jpaCommentDao = new JpaCommentDao(entityManagerComment);
        jpaUserDao = new JpaUserDao(entityManagerUser);

        entityTransaction = entityManagerNews.getTransaction();

        news = jpaNewsDao.getNewsById(1);
    }

    @After
    public void tearDown() throws Exception {
        entityManagerNews.close();
        entityManagerUser.close();
        entityManagerComment.close();
        entityManagerFactory.close();
    }

    @Test
    public void testCreateNews() throws Exception {
        News news = new News("News name", "Article name");
        entityTransaction.begin();
        News returned = jpaNewsDao.createNews(news);
        entityTransaction.commit();
        assertEquals(4, returned.getNewsId());
    }

    @Test
    public void testGetNewsById() throws Exception {
        News news = jpaNewsDao.getNewsById(1);
        assertEquals(news.getName(), "newsName1");
    }

    @Test
    public void testGetAllNews() throws Exception {
        List<News> list = jpaNewsDao.getAllNews();
        assertEquals(3, list.size());
    }

    @Test
    public void testGetNewsByName() throws Exception {
        News news = jpaNewsDao.getNewsByName("newsName1");
        assertEquals(1, news.getNewsId());
    }

    @Test
    public void testDeleteNews() throws Exception {
        News news = jpaNewsDao.getNewsByName("newsName1");
        entityTransaction.begin();
        jpaNewsDao.deleteNews(news);
        entityTransaction.commit();
        News news2 = jpaNewsDao.getNewsByName("newsName1");
        assertNull(news2);
    }

    @Test
    public void testUpdateNews() throws Exception {
        News news = jpaNewsDao.getNewsById(1);
        news.setName("updatedName");
        News returnedNews = jpaNewsDao.updateNews(news);
        News updatedNews = jpaNewsDao.getNewsById(1);

        assertEquals(returnedNews.getName(), "updatedName");
        assertEquals(updatedNews.getName(), "updatedName");
    }

    @Test
    public void testNewsHaveComments() throws Exception{
        Comment comment1 = jpaCommentDao.getCommentById(1);
        Comment comment2 = jpaCommentDao.getCommentById(2);

        List<Comment> commentList = new ArrayList();
        commentList.add(comment1);
        commentList.add(comment2);

        news.setComments(commentList);

        News updatedNews = jpaNewsDao.getNewsById(1);

        assertEquals(comment1, updatedNews.getComments().get(0));
        assertEquals(comment2, updatedNews.getComments().get(1));
    }

    @Test
    public void testGetSortedListOnVotes() throws Exception{
        User user1 = jpaUserDao.getUserById(1);
        User user2 = jpaUserDao.getUserById(2);
        User user3 = jpaUserDao.getUserById(3);

        News news1 = jpaNewsDao.getNewsById(1);
        News news2 = jpaNewsDao.getNewsById(2);
        News news3 = jpaNewsDao.getNewsById(3);

        NewsUtil newsUtil = new NewsUtil();
        newsUtil.setJpaNewsDao(jpaNewsDao);

        newsUtil.upVote(user1, news1);
        newsUtil.upVote(user2, news1);

        newsUtil.upVote(user2, news3);

        newsUtil.upVote(user1, news2);
        newsUtil.upVote(user2, news2);
        newsUtil.upVote(user3, news2);

        List<News> news = jpaNewsDao.getAllNewsSortedOnVotesEver();

        assertTrue(news.get(0).getVotes() > news.get(1).getVotes() &&
            news.get(1).getVotes() > news.get(2).getVotes());
    }

    @Test
    public void testGetListSortedOnDate() throws Exception{
        News news1 = new News("Some1", "news1");
        News news2 = new News("Some2", "news2");
        News news3 = new News("Some3", "news3");

        entityTransaction.begin();
        jpaNewsDao.createNews(news2);
        jpaNewsDao.createNews(news1);
        jpaNewsDao.createNews(news3);
        entityTransaction.commit();

        List<News> news = jpaNewsDao.getAllNewsSortedOnDate();

        assertEquals(news.get(3).getName(), "Some2");
        assertEquals(news.get(4).getName(), "Some1");
        assertEquals(news.get(5).getName(), "Some3");
    }

    @Test
    public void testDateGettingSetWhenCreatingNews()throws Exception {
        News news = new News("sadad", "sadsad");
        News newsCreated = jpaNewsDao.createNews(news);
        assertNotNull(newsCreated.getCreatedAt());
    }

    @Ignore
    @Test
    public void testDateGettingUpdatedWhenUpdatingNews()throws Exception {
        News news = new News("sadad", "sadsad");
        News n = jpaNewsDao.createNews(news);

        Thread.sleep(100);

        News updated = jpaNewsDao.updateNews(n);

        assertTrue(n.getCreatedAt().before(updated.getUpdatedAt()));
    }

    @Test
    public void testGetAllNewsSortedOnRatings() throws Exception {
        User user1 = jpaUserDao.getUserById(1);
        User user2 = jpaUserDao.getUserById(2);
        User user3 = jpaUserDao.getUserById(3);

        News news1 = jpaNewsDao.getNewsById(1);
        News news2 = jpaNewsDao.getNewsById(2);
        News news3 = jpaNewsDao.getNewsById(3);

        NewsUtil newsUtil = new NewsUtil();
        newsUtil.setJpaNewsDao(jpaNewsDao);

        newsUtil.upVote(user1, news1);
        newsUtil.upVote(user2, news1);

        newsUtil.upVote(user2, news3);

        newsUtil.upVote(user1, news2);
        newsUtil.upVote(user2, news2);
        newsUtil.upVote(user3, news2);

        System.out.println("News1; " + jpaNewsDao.getNewsById(1).getVotes());
        System.out.println("News2; " + jpaNewsDao.getNewsById(2).getVotes());
        System.out.println("News3; " + jpaNewsDao.getNewsById(3).getVotes());


        /* News 2 flest votes */
        Comment comment1 = jpaCommentDao.getCommentById(1);
        Comment comment2 = jpaCommentDao.getCommentById(2);
        Comment comment3 = jpaCommentDao.getCommentById(3);

        List<Comment> commentList = new ArrayList();
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);

        //* News 1 flest comments *//*
        news1.setComments(commentList);

        List<News> hottestNews = jpaNewsDao.getAllNewsSortedOnRatings();

        assertTrue(hottestNews.get(0).getRating() > hottestNews.get(1).getRating());
        assertTrue(hottestNews.get(1).getRating() > hottestNews.get(2).getRating());
    }
}