package ninja.idar.models;

import ninja.idar.models.Comment;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class CommentValidationTest {
    private ValidatorFactory validatorFactory;
    private Validator validator;
    private Set<ConstraintViolation<Comment>> validations;
    private Comment comment;

    @Before
    public void setUp() throws Exception {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        comment = new Comment("comment");
        validations = null; // reset for each
    }

    @Test
    public void testEmptyComment() throws Exception {
        comment = new Comment();
        validations = validator.validate(comment);
        assertEquals("empty comment should have 1 violation", 1, validations.size());
    }

    @Test
    public void testLegalComment() throws Exception {
        validations = validator.validate(comment);
        assertEquals("Legal comment should have no violation", 0, validations.size());
    }
}
