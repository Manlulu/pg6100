package ninja.idar.models;

import helpers.GenericBeanTestValidationHelper;
import helpers.StringHelper;
import org.junit.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
public class PostValidationTests extends GenericBeanTestValidationHelper<Post> {
    private static StringHelper stringHelper;
    private Post post;

    @BeforeClass
    public static void beforeTests(){
        stringHelper = new StringHelper();
    }

    @Before
    public void setUp() throws Exception {
        initLegalPost();
    }

    @AfterClass
    public static void afterTests(){
        closeValidationFactory();
    }

    @Test
    public void testEmptyPost() throws Exception {
        post = new Post();
        assertFalse("Empty post is not valid", isValid(post));
    }

    @Test
    public void testLegalPost() throws Exception {
        assertFalse("Legal post should have no validations", isValid(post));
    }

    @Test
    public void testContentsValidation() throws Exception {
        String longContents = stringHelper.buildStringOfLength(1001);
        String shortContents = stringHelper.buildStringOfLength(19);

        post.setContents(shortContents);
        assertFalse("19 characters should be too short content", isValidProperty(post, "contents"));

        post.setContents(longContents);
        assertFalse("1001 characters should be too long content", isValidProperty(post, "contents"));
    }

    @Test
    public void testTitleValidation() throws Exception {
        String longTitle = stringHelper.buildStringOfLength(31);
        String shortTitle = stringHelper.buildStringOfLength(4);

        post.setTitle(longTitle);
        assertFalse("title max cap should not be exceeded", isValidProperty(post, "title"));

        post.setTitle(shortTitle);
        assertFalse("title min cap should be exceeded", isValidProperty(post, "title"));

    }

    private void initLegalPost(){
        post = new Post("postTitle", "This is my post and I would like you to read it please");
    }
}