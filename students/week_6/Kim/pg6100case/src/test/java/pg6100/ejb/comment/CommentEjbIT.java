package pg6100.ejb.comment;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pg6100.ejb.news.NewsEJB;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.Comment;
import pg6100.entity.News;
import pg6100.entity.User;
import pg6100.util.DatabaseConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class CommentEjbIT {

    private NewsEJB newsEJB;
    private CommentEJB commentEJB;
    private UserEJB userEJB;

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

        newsEJB = new NewsEJB(entityManagerNews);
        commentEJB = new CommentEJB(entityManagerComment);
        userEJB = new UserEJB(entityManagerUser);

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
        Comment comment = new Comment("Article name");
        entityTransactionComment.begin();
        Comment returned = commentEJB.createComment(comment);
        entityTransactionComment.commit();
        assertEquals(4, returned.getCommentId());
    }

    @Test
    public void testGetCommentById() throws Exception {
        Comment comment = commentEJB.getCommentById(1);
        assertNotNull(comment);
    }

    @Test
    public void testCommentHaveUser() throws Exception {
        Comment comment = commentEJB.getCommentById(1);
        assertEquals(User.class, comment.getUser().getClass());
    }

    @Test
    public void testGetAllComment() throws Exception {
        List<Comment> list = commentEJB.getAllComment();
        assertEquals(3, list.size());
    }

    @Test
    public void testDeleteComment() throws Exception {
        Comment comment = commentEJB.getCommentById(1);
        entityTransactionComment.begin();
        commentEJB.deleteComment(comment);
        entityTransactionComment.commit();
        Comment comment2 = commentEJB.getCommentById(1);
        assertNull(comment2);
    }

    @Test
    public void testUpdateComment() throws Exception {
        Comment comment = commentEJB.getCommentById(1);
        comment.setBody("updatedBody");
        Comment returnedComment = commentEJB.updateComment(comment);
        Comment updatedComment = commentEJB.getCommentById(1);

        assertEquals(returnedComment.getBody(), "updatedBody");
        assertEquals(updatedComment.getBody(), "updatedBody");
    }

    @Test
    public void testFindNewsByComment() throws Exception{
        Comment comment = commentEJB.getCommentById(1);

        List<Comment> commentList = new ArrayList();
        commentList.add(comment);

        News news = newsEJB.getNewsById(1);
        news.setComments(commentList);

        Comment commentWithNews = commentEJB.getCommentById(1);

        assertEquals(news, commentWithNews.getNews());
    }

    @Test
    public void testGetSortedList() throws Exception{
        User user1 = userEJB.getUserById(1);
        User user2 = userEJB.getUserById(2);
        User user3 = userEJB.getUserById(3);

        Comment comment1 = commentEJB.getCommentById(1);
        Comment comment2 = commentEJB.getCommentById(2);
        Comment comment3 = commentEJB.getCommentById(3);

        CommentUtil commentUtil = new CommentUtil();
        commentUtil.setCommentEJB(commentEJB);

        commentUtil.upVote(user1, comment1);
        commentUtil.upVote(user2, comment1);

        commentUtil.upVote(user2, comment3);

        commentUtil.upVote(user1, comment2);
        commentUtil.upVote(user2, comment2);
        commentUtil.upVote(user3, comment2);

        List<Comment> comments = commentEJB.getAllCommentsSortedOnVotesEver();

        assertTrue(comments.get(0).getVotes() > comments.get(1).getVotes() &&
            comments.get(1).getVotes() > comments.get(2).getVotes());
    }

    @Test
    public void testGetSortedListOnDate() throws Exception{
        Comment comment1 = new Comment("comment1");
        Comment comment2 = new Comment("comment2");
        Comment comment3 = new Comment("comment3");

        entityTransactionComment.begin();
        commentEJB.createComment(comment2);
        commentEJB.createComment(comment1);
        commentEJB.createComment(comment3);
        entityTransactionComment.commit();

        List<Comment> comments = commentEJB.getAllCommentsSortedOnDate();

        assertEquals(comments.get(3).getBody(), "comment2");
        assertEquals(comments.get(4).getBody(), "comment1");
        assertEquals(comments.get(5).getBody(), "comment3");
    }

    @Test
    public void testCommentContainsComments() throws Exception {
        Comment parentComment = commentEJB.getCommentById(1);

        Comment childComment1 = commentEJB.getCommentById(2);
        Comment childComment2 = commentEJB.getCommentById(3);

        parentComment.addComment(childComment1);
        parentComment.addComment(childComment2);

        entityTransactionComment.begin();
        commentEJB.updateComment(parentComment);
        entityTransactionComment.commit();

        Comment newCom = commentEJB.getCommentById(1);

        assertEquals(2, newCom.getComments().size());
    }


}