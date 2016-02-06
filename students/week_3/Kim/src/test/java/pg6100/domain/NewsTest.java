package pg6100.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

import static org.junit.Assert.*;


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
        news = new News("Awesome news", "Awesome article");
        Comment comment1 = new Comment("Title", "This news is cool");
        Comment comment2 = new Comment("Bad title", "This is not good news");
        List<Comment> commentList = new ArrayList();
        commentList.add(comment1);
        commentList.add(comment2);
        news.setComments(commentList);

        assertEquals(2, news.getComments().size());
    }

    @Test
    public void testUserVotes() throws Exception {
        news = new News("A headline", "An article");

        Map<User, Integer> map = new HashMap<>();

        User user1 = new User("Valid username 1");
        User user2 = new User("Valid username 1");
        User user4 = new User("Valid username 1");

        map.put(user1, 1);
        map.put(user2, 1);
        map.put(user1, 1);
        map.put(user4, 1);

        news.setUserVotes(map);
        assertEquals(3, news.getVotes());
    }

    private void printViolations(Set<ConstraintViolation<News>> violations) throws Exception {
        System.out.println("Violations: ");
        for (ConstraintViolation<News> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}