package pg6100.ejb.comment;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.Comment;
import pg6100.entity.User;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;


public class CommentUtilTest {

    private Comment comment;
    private CommentUtil commentUtil;
    private User user1;
    private User user2;
    private User user3;

    private CommentEJB commentEJB;
    private UserEJB userEJB;


    private EntityManager entityManagerUser;
    private EntityManager entityManagerComment;

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);
        entityManagerUser = entityManagerFactory.createEntityManager();
        entityManagerComment = entityManagerFactory.createEntityManager();

        userEJB = new UserEJB(entityManagerUser);
        commentEJB = new CommentEJB(entityManagerComment);

        user1 = userEJB.getUserById(1);
        user2 = userEJB.getUserById(2);
        user3 = userEJB.getUserById(3);

        commentUtil = new CommentUtil();
        commentUtil.setCommentEJB(commentEJB);

        comment = commentEJB.getCommentById(1);

    }

    @After
    public void tearDown() throws Exception {
        entityManagerComment.close();
        entityManagerUser.close();
        entityManagerFactory.close();
    }

    @Ignore
    @Test
    public void testUpVote() throws Exception{
        commentUtil.upVote(user1, comment);
        commentUtil.upVote(user1, comment);

        commentUtil.upVote(user2, comment);
        commentUtil.upVote(user3, comment);

        Comment newComment = commentEJB.getCommentById(1);
        assertEquals(2, newComment.getVotes());
    }
    @Ignore
    @Test
    public void testDownVote() throws Exception{
        commentUtil.downVote(user1, comment);
        commentUtil.downVote(user1, comment);

        commentUtil.downVote(user2, comment);
        commentUtil.downVote(user3, comment);

        Comment newComment = commentEJB.getCommentById(1);
        assertEquals(-2, newComment.getVotes());
    }
    @Ignore
    @Test
    public void testDownAndUpVotes() throws Exception {
        commentUtil.downVote(user1, comment);
        commentUtil.upVote(user1, comment);

        Comment newComment = commentEJB.getCommentById(1);
        assertEquals(1, newComment.getVotes());
    }
    @Ignore
    @Test
    public void testUpAndDownVotes() throws Exception {
        commentUtil.upVote(user1, comment);
        commentUtil.downVote(user1, comment);

        Comment newComment = commentEJB.getCommentById(1);
        assertEquals(-1, newComment.getVotes());
    }

    @Ignore
    @Test
    public void testGetVotedComment() throws Exception{
        Comment comment = commentEJB.getCommentById(1);

        User user1 = userEJB.getUserById(1);
        User user2 = userEJB.getUserById(2);
        User user3 = userEJB.getUserById(3);

        CommentUtil commentUtil = new CommentUtil();
        commentUtil.setCommentEJB(commentEJB);

        commentUtil.upVote(user1, comment);
        commentUtil.upVote(user2, comment);
        commentUtil.upVote(user3, comment);
        commentUtil.upVote(user2, comment);

        Comment newComment = commentEJB.getCommentById(1);
        assertEquals(2, newComment.getVotes());
    }

}