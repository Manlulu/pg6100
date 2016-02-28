package pg6100.ejb.comment;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pg6100.entity.Comment;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;


public class CommentStatisticsTest {

    private CommentStatistics commentStatistics;
    private CommentEJB commentEJB;
    private EntityManager entityManagerComment;
    private EntityTransaction entityTransaction;
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {

        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);

        entityManagerComment = entityManagerFactory.createEntityManager();

        commentEJB = new CommentEJB(entityManagerComment);

        commentStatistics = new CommentStatistics();
        commentStatistics.setCommentEJB(commentEJB);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Ignore
    @Test
    public void testGetTotalNumberOfComments() throws Exception {
        assertEquals(3, commentStatistics.getTotalNumberOfComments());
    }


    @Test
    public void testGetNumberOfCommentsThisDay() throws Exception {
        Comment comment1 = new Comment("ASdfg1");
        Comment comment2 = new Comment("ASdfg2");
        Comment comment3 = new Comment("ASdfg3");

        entityTransaction = entityManagerComment.getTransaction();
        entityTransaction.begin();
        commentEJB.createComment(comment1);
        commentEJB.createComment(comment2);
        commentEJB.createComment(comment3);
        entityTransaction.commit();

        int numberOfNewsThisDay = commentStatistics.getNumberOfCommentsThisDay();

        assertEquals(3, numberOfNewsThisDay);
    }

    @Ignore
    @Test
    public void testGetNumberOfCommentsOnOneComment() throws Exception {
        Comment parentComment = commentEJB.getCommentById(1);

        Comment childComment1 = commentEJB.getCommentById(2);
        Comment childComment2 = commentEJB.getCommentById(3);
        Comment childComment3 = new Comment("com4");

        parentComment.addComment(childComment1);
        parentComment.addComment(childComment2);
        parentComment.addComment(childComment3);

        Comment updatedParent = commentEJB.getCommentById(1);

        assertEquals(3, commentStatistics.getNumberOfCommentsOnOneComment(updatedParent));
    }

}