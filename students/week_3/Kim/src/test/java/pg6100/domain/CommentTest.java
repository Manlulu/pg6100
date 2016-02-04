package pg6100.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;


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
        comment = new Comment("A name", "Body of the comment");

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        printViolations(violations);

        assertEquals(0, violations.size());
    }

    @Test
    public void testNameMax64Letters() throws Exception {
        comment.setName("asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf1");
        comment.setBody("Valid body");

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        printViolations(violations);

        assertEquals(1, violations.size());
    }

    @Test
    public void testBodyMax300Letters() throws Exception {
        comment.setName("Valid name 123");
        comment.setBody("12bcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde");

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        printViolations(violations);

        assertEquals(1, violations.size());
    }

    @Test
    public void testNameCanNotBeNull() throws Exception {
        comment = new Comment(null, "Valid article");

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        printViolations(violations);

        assertEquals(1, violations.size());
    }

    @Test
    public void testBodyCanNotBeNull() throws Exception {
        comment = new Comment("Valid Name", null);

        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        printViolations(violations);

        assertEquals(1, violations.size());
    }

    @Test
    public void testCommentCanOnlyBeSetOnce() throws Exception {
        News news = new News("Nyhet", "Artikken");

        Comment comment1 = new Comment("Comment1", "com1");
        Comment comment2 = new Comment("Comment2", "com2");

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