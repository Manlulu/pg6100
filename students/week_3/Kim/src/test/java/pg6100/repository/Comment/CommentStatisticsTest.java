package pg6100.repository.Comment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pg6100.domain.Comment;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;


public class CommentStatisticsTest {

    private CommentStatistics commentStatistics;
    private JpaCommentDao jpaCommentDao;
    private EntityManager entityManagerComment;
    private EntityTransaction entityTransaction;
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {

        entityManagerFactory = Persistence.createEntityManagerFactory(DatabaseConfig.database_name);

        entityManagerComment = entityManagerFactory.createEntityManager();

        jpaCommentDao = new JpaCommentDao(entityManagerComment);

        commentStatistics = new CommentStatistics();
        commentStatistics.setJpaCommentDao(jpaCommentDao);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetTotalNumberOfComments() throws Exception {
        assertEquals(3, commentStatistics.getTotalNumberOfComments());
    }

    @Test
    public void testGetNumberOfCommentsThisDay() throws Exception {
        Comment comment1 = new Comment("Comment1", "ASdfg1");
        Comment comment2 = new Comment("Comment2", "ASdfg2");
        Comment comment3 = new Comment("Comment3", "ASdfg3");

        entityTransaction = entityManagerComment.getTransaction();
        entityTransaction.begin();
        jpaCommentDao.createComment(comment1);
        jpaCommentDao.createComment(comment2);
        jpaCommentDao.createComment(comment3);
        entityTransaction.commit();

        int numberOfNewsThisDay = commentStatistics.getNumberOfCommentsThisDay();

        assertEquals(3, numberOfNewsThisDay);
    }

    @Test
    public void testGetNumberOfCommentsOnOneComment() throws Exception {
        Comment parentComment = jpaCommentDao.getCommentById(1);

        Comment childComment1 = jpaCommentDao.getCommentById(2);
        Comment childComment2 = jpaCommentDao.getCommentById(3);
        Comment childComment3 = new Comment("Comment4", "com4");

        parentComment.addComment(childComment1);
        parentComment.addComment(childComment2);
        parentComment.addComment(childComment3);

        Comment updatedParent = jpaCommentDao.getCommentById(1);

        assertEquals(3, commentStatistics.getNumberOfCommentsOnOneComment(updatedParent));
    }
}