package ninja.idar.models;

import ninja.idar.helpers.GenericBeanValidationTestHelper;
import ninja.idar.helpers.StringHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class CommentValidationTest extends GenericBeanValidationTestHelper<Comment> {
    private Comment comment;
    private StringHelper stringHelper;

    @Before
    public void setUp() throws Exception {
        stringHelper = new StringHelper();
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

    @Test
    public void testCommentContentsValidation() throws Exception {
        comment.setContents(stringHelper.buildStringOfLength(2));
        assertFalse("comment min length cap should be exceeded", isValidProperty(comment, "contents"));

        comment.setContents(stringHelper.buildStringOfLength(1001));
        assertFalse("comment length cap should not be exceeded", isValidProperty(comment, "contents"));

    }
}
