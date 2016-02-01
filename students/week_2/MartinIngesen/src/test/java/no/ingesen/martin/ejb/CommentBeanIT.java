package no.ingesen.martin.ejb;

import no.ingesen.martin.entity.Comment;
import org.junit.Test;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;

public class CommentBeanIT extends ITBase {
    @Test
    public void testPersist() throws Exception {
        Comment comment = new Comment();

        comment.setCommenter(userBean.findById(1));
        comment.setPost(postBean.findById(1));
        comment.setContent("This is some content");
        comment.setCommented(LocalDateTime.now());

        entityManager.getTransaction().begin();
        commentBean.persist(comment);
        entityManager.getTransaction().commit();

        assertTrue(comment.getId() > 0);
    }

    @Test
    public void testFindById() throws Exception {
        Comment comment = commentBean.findById(1);
        assertTrue(comment.getId() == 1);
    }
}
