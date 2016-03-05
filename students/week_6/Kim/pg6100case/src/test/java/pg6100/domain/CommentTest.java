package pg6100.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pg6100.entity.Comment;
import pg6100.entity.News;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class CommentTest {

    private Validator validator;
    private Comment comment;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        comment = new Comment();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testValidComment() throws Exception {
        comment = new Comment("Body of the Comment");

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        printViolations(violations);

        assertEquals(0, violations.size());
    }
    @Test
    public void testBodyMax300Letters() throws Exception {
        comment.setBody("12bcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde");

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        printViolations(violations);

        assertEquals(1, violations.size());
    }



    @Test
    public void testBodyCanNotBeNull() throws Exception {
        comment = new Comment(null);

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        printViolations(violations);

        assertEquals(1, violations.size());
    }

    @Test
    public void testCommentCanOnlyBeSetOnce() throws Exception {
        News news = new News("Nyhet", "Artikken");

        Comment comment1 = new Comment("com1");
        Comment comment2 = new Comment("com2");

        news.addComment(comment1);
        comment2.addComment(comment1);

        assertEquals(1, news.getComments().size());
        assertEquals(0, comment2.getComments().size());
    }

    private void printViolations(Set<ConstraintViolation<Comment>> violations) throws Exception {
        System.out.println("Violations: ");
        for (ConstraintViolation<Comment> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}