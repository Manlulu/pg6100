package ninja.idar.models;

import helpers.GenericBeanTestValidationHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class CommentValidationTest extends GenericBeanTestValidationHelper<Comment> {
    private Comment comment;

    @Before
    public void setUp() throws Exception {
        comment = new Comment("comment");
    }

    @AfterClass
    public static void afterTests() throws Exception {
        closeValidationFactory();
    }

    @Test
    public void testEmptyComment() throws Exception {
        comment = new Comment();
        assertFalse("Empty comment object is not valid", isValid(comment));
    }

    @Test
    public void testLegalComment() throws Exception {
        assertTrue("Legal object should be valid", isValid(comment));
    }
}
