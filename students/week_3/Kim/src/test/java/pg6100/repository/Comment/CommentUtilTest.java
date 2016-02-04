package pg6100.repository.Comment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pg6100.domain.Comment;
import pg6100.domain.User;
import pg6100.repository.Comment.CommentUtil;
import pg6100.repository.News.JpaNewsDao;
import pg6100.repository.User.JpaUserDao;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;


public class CommentUtilTest {

    private Comment comment;
    private CommentUtil commentUtil;
    private User user1;
    private User user2;
    private User user3;

    private JpaCommentDao jpaCommentDao;
    private JpaUserDao jpaUserDao;


    private EntityManager entityManagerUser;
    private EntityManager entityManagerComment;

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);
        entityManagerUser = entityManagerFactory.createEntityManager();
        entityManagerComment = entityManagerFactory.createEntityManager();

        jpaUserDao = new JpaUserDao(entityManagerUser);
        jpaCommentDao = new JpaCommentDao(entityManagerComment);

        user1 = jpaUserDao.getUserById(1);
        user2 = jpaUserDao.getUserById(2);
        user3 = jpaUserDao.getUserById(3);

        commentUtil = new CommentUtil();
        commentUtil.setJpaCommentDao(jpaCommentDao);

        comment = jpaCommentDao.getCommentById(1);

    }

    @After
    public void tearDown() throws Exception {
        entityManagerComment.close();
        entityManagerUser.close();
        entityManagerFactory.close();
    }

    @Test
    public void testUpVote() throws Exception{
        commentUtil.upVote(user1, comment);
        commentUtil.upVote(user1, comment);

        commentUtil.upVote(user2, comment);
        commentUtil.upVote(user3, comment);

        Comment newComment = jpaCommentDao.getCommentById(1);
        assertEquals(2, newComment.getVotes());
    }

    @Test
    public void testDownVote() throws Exception{
        commentUtil.downVote(user1, comment);
        commentUtil.downVote(user1, comment);

        commentUtil.downVote(user2, comment);
        commentUtil.downVote(user3, comment);

        Comment newComment = jpaCommentDao.getCommentById(1);
        assertEquals(-2, newComment.getVotes());
    }

    @Test
    public void testDownAndUpVotes() throws Exception {
        commentUtil.downVote(user1, comment);
        commentUtil.upVote(user1, comment);

        Comment newComment = jpaCommentDao.getCommentById(1);
        assertEquals(1, newComment.getVotes());
    }

    @Test
    public void testUpAndDownVotes() throws Exception {
        commentUtil.upVote(user1, comment);
        commentUtil.downVote(user1, comment);

        Comment newComment = jpaCommentDao.getCommentById(1);
        assertEquals(-1, newComment.getVotes());
    }

    @Test
    public void testGetVotedComment() throws Exception{
        Comment comment = jpaCommentDao.getCommentById(1);

        User user1 = jpaUserDao.getUserById(1);
        User user2 = jpaUserDao.getUserById(2);
        User user3 = jpaUserDao.getUserById(3);

        CommentUtil commentUtil = new CommentUtil();
        commentUtil.setJpaCommentDao(jpaCommentDao);

        commentUtil.upVote(user1, comment);
        commentUtil.upVote(user2, comment);
        commentUtil.upVote(user3, comment);
        commentUtil.upVote(user2, comment);

        Comment newComment = jpaCommentDao.getCommentById(1);
        assertEquals(2, newComment.getVotes());
    }
}