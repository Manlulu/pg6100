package pg6100.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pg6100.entity.Comment;
import pg6100.entity.News;
import pg6100.entity.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

import static org.junit.Assert.assertEquals;


public class NewsTest {

    private Validator validator;

    private News news;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        news = new News();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidNews() throws Exception {
        news = new News("A headline", "An article");

        Set<ConstraintViolation<News>> violations = validator.validate(news);
        printViolations(violations);

        assertEquals(0, violations.size());
    }

    @Test
    public void testNameMax64Letters() throws Exception {
        news.setName("asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf asdf1");
        news.setArticle("Valid article");

        Set<ConstraintViolation<News>> violations = validator.validate(news);
        printViolations(violations);

        assertEquals(1, violations.size());
    }

    @Test
    public void testArticleMax300Letters() throws Exception {
        news.setName("Valid name 123");
        news.setArticle("12bcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde");

        Set<ConstraintViolation<News>> violations = validator.validate(news);
        printViolations(violations);

        assertEquals(1, violations.size());
    }

    @Test
    public void testNameCanNotBeNull() throws Exception {
        news = new News(null, "Valid article");

        Set<ConstraintViolation<News>> violations = validator.validate(news);
        printViolations(violations);

        assertEquals(1, violations.size());
    }

    @Test
    public void testArticleCanBeNull() throws Exception {
        news = new News("Valid Name", null);

        Set<ConstraintViolation<News>> violations = validator.validate(news);
        printViolations(violations);

        assertEquals(0, violations.size());
    }

    @Test
    public void testNewsContainsComments() throws Exception {
        news = new News("Awesome News", "Awesome article");
        Comment comment1 = new Comment("This News is cool");
        Comment comment2 = new Comment("This is not good News");
        List<Comment> commentList = new ArrayList();
        commentList.add(comment1);
        commentList.add(comment2);
        news.setComments(commentList);

        assertEquals(2, news.getComments().size());
    }


    private void printViolations(Set<ConstraintViolation<News>> violations) throws Exception {
        System.out.println("Violations: ");
        for (ConstraintViolation<News> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}