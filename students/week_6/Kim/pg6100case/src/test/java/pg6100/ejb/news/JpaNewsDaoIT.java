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
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class JpaNewsDaoIT {

    private News news;

    private NewsEJB newsEJB;
    private CommentEJB commentEJB;
    private UserEJB userEJB;

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

        newsEJB = new NewsEJB(entityManagerNews);
        commentEJB = new CommentEJB(entityManagerComment);
        userEJB = new UserEJB(entityManagerUser);

        entityTransaction = entityManagerNews.getTransaction();

        news = newsEJB.getNewsById(1);
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
        News returned = newsEJB.createNews(news);
        entityTransaction.commit();
        assertEquals(4, returned.getNewsId());
    }

    @Test
    public void testCreateNewsWithAllFields() throws Exception {
        User user = userEJB.getUserById(1);
        List<Comment> commentList = commentEJB.getAllComment();
        List<User> userList = userEJB.getAllUsers();

        News news = new News("News name", "This is an article", user);

        news.setComments(commentList);
        news.setUsersUpVote(userList);

        entityTransaction.begin();
        News returned = newsEJB.createNews(news);

        assertNotNull(returned);
        assertEquals(returned.getName(), "News name");
        assertEquals(returned.getArticle(), "This is an article");
        assertTrue(returned.getCreatedAt().before(new Date()));
        assertTrue(returned.getUpdatedAt().before(new Date()));
        assertEquals(returned.getUser(), user);
        assertEquals(returned.getComments().size(), 3);
        assertEquals(returned.getUsersUpVote().size(), 3);
        assertEquals(returned.getRating(), 6);

        if (!entityTransaction.getRollbackOnly())
            entityTransaction.commit();

        returned.addComment(new Comment());
        assertEquals(returned.getRating(), 7);

    }

    @Test
    public void testGetNewsById() throws Exception {
        News news = newsEJB.getNewsById(1);
        assertEquals(news.getName(), "newsName1");
    }

    @Test
    public void testGetAllNews() throws Exception {
        List<News> list = newsEJB.getAllNews();
        assertEquals(3, list.size());
    }

    @Test
    public void testGetNewsByName() throws Exception {
        News news = newsEJB.getNewsByName("newsName1");
        assertEquals(1, news.getNewsId());
    }

    @Test
    public void testDeleteNews() throws Exception {
        News news = newsEJB.getNewsByName("newsName1");

        EntityTransaction entityTransaction1 = entityManagerComment.getTransaction();
        entityTransaction1.begin();
        for (Comment comment : commentEJB.getAllComment()) {
            if (comment.getNews().equals(news)) {
                commentEJB.deleteComment(comment);
            }
        }
        entityTransaction1.commit();

        EntityTransaction entityTransaction2 = entityManagerUser.getTransaction();
        entityTransaction2.begin();

        for (User user : userEJB.getAllUsers()) {
            if (user.equals(news.getUser())) {
                userEJB.deleteUser(user);
            }
        }

        entityTransaction2.commit();

        entityTransaction.begin();
        newsEJB.deleteNews(news);
        entityTransaction.commit();
        News news2 = newsEJB.getNewsByName("newsName1");
        assertNull(news2);
    }

    @Test
    public void testUpdateNews() throws Exception {
        News news = newsEJB.getNewsById(1);
        news.setName("updatedName");
        News returnedNews = newsEJB.updateNews(news);
        News updatedNews = newsEJB.getNewsById(1);

        assertEquals(returnedNews.getName(), "updatedName");
        assertEquals(updatedNews.getName(), "updatedName");
    }

    @Test
    public void testNewsHaveComments() throws Exception {
        Comment comment1 = commentEJB.getCommentById(1);
        Comment comment2 = commentEJB.getCommentById(2);

        List<Comment> commentList = new ArrayList();
        commentList.add(comment1);
        commentList.add(comment2);

        news.setComments(commentList);

        News updatedNews = newsEJB.getNewsById(1);

        assertEquals(comment1, updatedNews.getComments().get(0));
        assertEquals(comment2, updatedNews.getComments().get(1));
    }


    @Test
    public void testGetSortedListOnVotes() throws Exception {
        User user1 = userEJB.getUserById(1);
        User user2 = userEJB.getUserById(2);
        User user3 = userEJB.getUserById(3);

        News news1 = newsEJB.getNewsById(1);
        News news2 = newsEJB.getNewsById(2);
        News news3 = newsEJB.getNewsById(3);

        NewsUtil newsUtil = new NewsUtil();
        newsUtil.setNewsEJB(newsEJB);

        newsUtil.upVote(user1, news1);
        newsUtil.upVote(user2, news1);

        newsUtil.upVote(user2, news3);

        newsUtil.upVote(user1, news2);
        newsUtil.upVote(user2, news2);
        newsUtil.upVote(user3, news2);

        List<News> news = newsEJB.getAllNewsSortedOnVotesEver();

        assertTrue(news.get(0).getVotes() > news.get(1).getVotes() &&
                news.get(1).getVotes() > news.get(2).getVotes());
    }

    @Test
    public void testGetListSortedOnDate() throws Exception {
        News news1 = new News("Some1", "news1");
        News news2 = new News("Some2", "news2");
        News news3 = new News("Some3", "news3");

        entityTransaction.begin();
        newsEJB.createNews(news2);
        newsEJB.createNews(news1);
        newsEJB.createNews(news3);
        entityTransaction.commit();

        List<News> news = newsEJB.getAllNewsSortedOnDate();

        assertEquals(news.get(3).getName(), "Some2");
        assertEquals(news.get(4).getName(), "Some1");
        assertEquals(news.get(5).getName(), "Some3");
    }

    @Test
    public void testDateGettingSetWhenCreatingNews() throws Exception {
        News news = new News("sadad", "sadsad");
        News newsCreated = newsEJB.createNews(news);
        assertNotNull(newsCreated.getCreatedAt());
    }


    @Test
    public void testDateGettingUpdatedWhenUpdatingNews() throws Exception {
        News news = new News("sadad", "sadsad");
        news = newsEJB.createNews(news);
        Thread.sleep(1000);

        news.setArticle("This is a new article");
        newsEJB.updateNews(news);

        assertTrue(news.getCreatedAt().before(news.getUpdatedAt()));
    }


    @Test
    public void testGetAllNewsSortedOnRatings() throws Exception {
        User user1 = userEJB.getUserById(1);
        User user2 = userEJB.getUserById(2);
        User user3 = userEJB.getUserById(3);

        News news1 = newsEJB.getNewsById(1);
        News news2 = newsEJB.getNewsById(2);
        News news3 = newsEJB.getNewsById(3);

        NewsUtil newsUtil = new NewsUtil();
        newsUtil.setNewsEJB(newsEJB);

        newsUtil.upVote(user1, news1);
        newsUtil.upVote(user2, news1);

        newsUtil.upVote(user2, news3);

        newsUtil.upVote(user1, news2);
        newsUtil.upVote(user2, news2);
        newsUtil.upVote(user3, news2);

        System.out.println("News1; " + newsEJB.getNewsById(1).getVotes());
        System.out.println("News2; " + newsEJB.getNewsById(2).getVotes());
        System.out.println("News3; " + newsEJB.getNewsById(3).getVotes());


        // News 2 flest votes
        Comment comment1 = commentEJB.getCommentById(1);
        Comment comment2 = commentEJB.getCommentById(2);
        Comment comment3 = commentEJB.getCommentById(3);

        List<Comment> commentList = new ArrayList();
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);

        // News 1 flest comments
        news1.setComments(commentList);

        List<News> hottestNews = newsEJB.getAllNewsSortedOnRatings();

        assertTrue(hottestNews.get(0).getRating() > hottestNews.get(1).getRating());
        assertTrue(hottestNews.get(1).getRating() > hottestNews.get(2).getRating());
    }

}