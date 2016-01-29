package ninja.idar.models.ValidationTests;

import ninja.idar.models.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
public class PostValidationTests {
    private ValidatorFactory validatorFactory;
    private Validator validator;
    private Set<ConstraintViolation<Post>> validations;
    private Post post;

    @Before
    public void setUp() throws Exception {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        initLegalPost();
    }

    @After
    public void tearDown() throws Exception {
        validatorFactory.close();
    }

    @Test
    public void testEmptyPost() throws Exception {
        post = new Post();
        Set<ConstraintViolation<Post>> validations = validator.validate(post);
        assertEquals("empty post should have 2 validations", 2, validations.size());
    }

    @Test
    public void testLegalPost() throws Exception {
        validations = validator.validate(post);
        validations.forEach(System.out::println);
        assertEquals("Legal post should have no validations", 0, validations.size());
    }

    @Test
    public void testContentsValidation() throws Exception {
        String shortContents = "";
        String longContents = "";

        longContents = buildStringOfLength(1001);

        shortContents = buildStringOfLength(19);

        post.setContents(shortContents);
        validations = validator.validate(post);
        assertEquals("19 characters should be too short content", 1, validations.size());

        post.setContents(longContents);
        validations = validator.validate(post);
        assertEquals("1001 characters should be too long content", 1, validations.size());
    }

    @Test
    public void testTitleValidation() throws Exception {
        String shortTitle = "";
        String longTitle = "";

        longTitle = buildStringOfLength(31);
        shortTitle = buildStringOfLength(4);

        post.setTitle(longTitle);
        validations = validator.validate(post);
        assertEquals("31 characters should be too short title", 1, validations.size());

        post.setTitle(shortTitle);
        validations = validator.validate(post);
        assertEquals("4 characters should be too short title", 1, validations.size());

    }

    private String buildStringOfLength(int length) {
        String string = "";
        for(int i = 0; i < length; i++){
            string += 'a';
        }

        return string;
    }

    private void initLegalPost(){
        post = new Post("postTitle", "This is my post and I would like you to read it please");
    }
}