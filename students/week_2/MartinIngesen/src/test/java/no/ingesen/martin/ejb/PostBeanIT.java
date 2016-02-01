package no.ingesen.martin.ejb;

import no.ingesen.martin.entity.Post;
import org.junit.Test;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;

public class PostBeanIT extends ITBase{
    @Test
    public void testPersist() throws Exception {
        Post post = new Post();

        post.setType(Post.Type.TEXT);
        post.setContent("Hello World");
        post.setTitle("Hola Mundo");
        post.setPosted(LocalDateTime.now());
        post.setPoster(userBean.findById(1));

        entityManager.getTransaction().begin();
        postBean.persist(post);
        entityManager.getTransaction().commit();

        assertTrue(post.getId() > 0);
    }

    @Test
    public void testFindById() throws Exception {
        Post post = postBean.findById(1);
        assertTrue(post.getId() == 1);
    }
}
