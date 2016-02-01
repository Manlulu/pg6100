package ninja.idar.service.commentservice;

import ninja.idar.helpers.GenericBeanIntegrationTestHelper;
import ninja.idar.helpers.StringHelper;
import ninja.idar.models.Comment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public class CommentJpaTest extends GenericBeanIntegrationTestHelper<Comment>{
    private CommentJpa commentJpa;
    private static final int NON_EXISTING_COMMENT_ID = 654321;
    private static final int EXISTING_COMMENT_ID = 1001;

    @Before
    public void setUp() throws Exception {
        commentJpa = new CommentJpa(getPersister());
    }

    @Test
    public void testGetAllComments() throws Exception {
        assertTrue("init.sql should populate comments", 0 < commentJpa.getAll().size());
    }

    @Test
    public void testGetCommentById() throws Exception {
        assertNull("Post with id " + NON_EXISTING_COMMENT_ID + " should not exist from init.sql", commentJpa.getById(NON_EXISTING_COMMENT_ID));
        assertNotNull("Post with id " + EXISTING_COMMENT_ID + " should exist from init.sql", commentJpa.getById(EXISTING_COMMENT_ID));
    }

    @Test
    public void testUpdateComment() throws Exception {
        Comment comment = commentJpa.getById(EXISTING_COMMENT_ID);
        String newContents = "new test content from the test itself";

        assertNotEquals("Comment should not have contents newContents by default", newContents, comment.getContents());

        comment.setContents(newContents);
        commentJpa.update(comment);

        comment = commentJpa.getById(EXISTING_COMMENT_ID);
        assertEquals("Comment should have contents newContents after update", newContents, comment.getContents());
    }

    @Test
    public void testDeleteCommentById() throws Exception {
        assertNotEquals("Comment should exist before deletion", null, commentJpa.getById(EXISTING_COMMENT_ID));
        commentJpa.deleteById(EXISTING_COMMENT_ID);
        assertEquals("Comment should not exist after deletion", null, commentJpa.getById(EXISTING_COMMENT_ID));
    }

    @Test
    public void testDeleteCommentByObject() throws Exception {
        Comment comment = commentJpa.getById(EXISTING_COMMENT_ID);
        assertNotEquals("Comment should exist before deletion", null, commentJpa.getById(comment.getId()));
        commentJpa.deleteByObject(comment);
        assertEquals("Comment should not exist after deletion", null, commentJpa.getById(comment.getId()));

    }

    @Test
    public void testPersistComment() throws Exception {
        Comment comment = new Comment(StringHelper.DEFAULT_TEST_CONTENTS);
        assertFalse("Comment should not have id prior to persisting", 0 < comment.getId());
        commentJpa.persist(comment);
        assertTrue("Comment should not have id post persisting", 0 < comment.getId());
    }

    @Test
    public void testClose() throws Exception {
        assertTrue("Before closing, persister should be open", getPersister().isOpen());
        commentJpa.close();
        assertFalse("After closing, persister should not be open", getPersister().isOpen());

    }

    @Test
    public void testGetVotes() throws Exception {
        assertEquals("comment 1001 should have total of 3 score", 3, commentJpa.getVotes(1001));
    }
}
