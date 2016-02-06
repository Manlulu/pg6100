package pg6100.repository.Comment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pg6100.domain.Comment;
import pg6100.domain.News;
import pg6100.domain.User;
import pg6100.repository.News.JpaNewsDao;
import pg6100.repository.News.NewsUtil;
import pg6100.repository.User.JpaUserDao;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class JpaCommentDaoIT {

    private JpaNewsDao jpaNewsDao;
    private JpaCommentDao jpaCommentDao;
    private JpaUserDao jpaUserDao;

    private EntityTransaction entityTransactionComment;

    private EntityManager entityManagerNews;
    private EntityManager entityManagerComment;

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);
        entityManagerNews = entityManagerFactory.createEntityManager();
        entityManagerComment = entityManagerFactory.createEntityManager();
        EntityManager entityManagerUser = entityManagerFactory.createEntityManager();

        jpaNewsDao = new JpaNewsDao(entityManagerNews);
        jpaCommentDao = new JpaCommentDao(entityManagerComment);
        jpaUserDao = new JpaUserDao(entityManagerUser);

        entityTransactionComment = entityManagerComment.getTransaction();
    }

    @After
    public void tearDown() throws Exception {
        entityManagerNews.close();
        entityManagerComment.close();
        entityManagerFactory.close();
    }

    @Test
    public void testCreateComment() throws Exception {
        Comment comment = new Comment("Comment name", "Article name");
        entityTransactionComment.begin();
        Comment returned = jpaCommentDao.createComment(comment);
        entityTransactionComment.commit();
        assertEquals(4, returned.getCommentId());
    }

    @Test
    public void testGetCommentById() throws Exception {
        Comment comment = jpaCommentDao.getCommentById(1);
        assertEquals(comment.getName(), "commentName1");
    }

    @Test
    public void testCommentHaveUser() throws Exception {
        Comment comment = jpaCommentDao.getCommentById(1);
        assertEquals(User.class, comment.getUser().getClass());
    }

    @Test
    public void testGetAllComment() throws Exception {
        List<Comment> list = jpaCommentDao.getAllComment();
        assertEquals(3, list.size());
    }

    @Test
    public void testGetCommentByName() throws Exception {
        Comment comment = jpaCommentDao.getCommentByName("commentName1");
        assertEquals(1, comment.getCommentId());
    }

    @Test
    public void testDeleteComment() throws Exception {
        Comment comment = jpaCommentDao.getCommentByName("commentName1");
        entityTransactionComment.begin();
        jpaCommentDao.deleteComment(comment);
        entityTransactionComment.commit();
        Comment comment2 = jpaCommentDao.getCommentByName("commentName1");
        assertNull(comment2);
    }

    @Test
    public void testUpdateComment() throws Exception {
        Comment comment = jpaCommentDao.getCommentById(1);
        comment.setName("updatedName");
        Comment returnedComment = jpaCommentDao.updateComment(comment);
        Comment updatedComment = jpaCommentDao.getCommentById(1);

        assertEquals(returnedComment.getName(), "updatedName");
        assertEquals(updatedComment.getName(), "updatedName");
    }

    @Test
    public void testFindNewsByComment() throws Exception{
        Comment comment = jpaCommentDao.getCommentById(1);

        List<Comment> commentList = new ArrayList();
        commentList.add(comment);

        News news = jpaNewsDao.getNewsById(1);
        news.setComments(commentList);

        Comment commentWithNews = jpaCommentDao.getCommentById(1);

        assertEquals(news, commentWithNews.getNews());
    }

    @Test
    public void testGetSortedList() throws Exception{
        User user1 = jpaUserDao.getUserById(1);
        User user2 = jpaUserDao.getUserById(2);
        User user3 = jpaUserDao.getUserById(3);

        Comment comment1 = jpaCommentDao.getCommentById(1);
        Comment comment2 = jpaCommentDao.getCommentById(2);
        Comment comment3 = jpaCommentDao.getCommentById(3);

        CommentUtil commentUtil = new CommentUtil();
        commentUtil.setJpaCommentDao(jpaCommentDao);

        commentUtil.upVote(user1, comment1);
        commentUtil.upVote(user2, comment1);

        commentUtil.upVote(user2, comment3);

        commentUtil.upVote(user1, comment2);
        commentUtil.upVote(user2, comment2);
        commentUtil.upVote(user3, comment2);

        List<Comment> comments = jpaCommentDao.getAllCommentsSortedOnVotesEver();

        assertTrue(comments.get(0).getVotes() > comments.get(1).getVotes() &&
            comments.get(1).getVotes() > comments.get(2).getVotes());
    }

    @Test
    public void testGetSortedListOnDate() throws Exception{
        Comment comment1 = new Comment("Some1", "comment1");
        Comment comment2 = new Comment("Some2", "comment2");
        Comment comment3 = new Comment("Some3", "comment3");

        entityTransactionComment.begin();
        jpaCommentDao.createComment(comment2);
        jpaCommentDao.createComment(comment1);
        jpaCommentDao.createComment(comment3);
        entityTransactionComment.commit();

        List<Comment> comments = jpaCommentDao.getAllCommentsSortedOnDate();

        assertEquals(comments.get(3).getName(), "Some2");
        assertEquals(comments.get(4).getName(), "Some1");
        assertEquals(comments.get(5).getName(), "Some3");
    }

    @Test
    public void testCommentContainsComments() throws Exception {
        Comment parentComment = jpaCommentDao.getCommentById(1);

        Comment childComment1 = jpaCommentDao.getCommentById(2);
        Comment childComment2 = jpaCommentDao.getCommentById(3);

        parentComment.addComment(childComment1);
        parentComment.addComment(childComment2);

        entityTransactionComment.begin();
        jpaCommentDao.updateComment(parentComment);
        entityTransactionComment.commit();

        Comment newCom = jpaCommentDao.getCommentById(1);

        assertEquals(2, newCom.getComments().size());
    }

}