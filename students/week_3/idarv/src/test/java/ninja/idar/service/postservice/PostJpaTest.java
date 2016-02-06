package ninja.idar.service.postservice;

import ninja.idar.helpers.GenericBeanIntegrationTestHelper;
import ninja.idar.helpers.PostTestHelper;
import ninja.idar.models.Post;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public class PostJpaTest extends GenericBeanIntegrationTestHelper<Post>{
    private PostJpa postJpa;
    private static final int EXISTING_POST_ID = 1001;
    private static final int NON_EXISTING_POST_ID = 654321;

    @Before
    public void setUp() throws Exception {
        postJpa = new PostJpa(getPersister());
    }

    @Test
    public void testGetPosts() throws Exception {
        assertTrue("init.sql should populate users", 0 < postJpa.getAll().size());
    }

    @Test
    public void testGetPostById() throws Exception {
        assertNull("Post with id " + NON_EXISTING_POST_ID + " should not exist from init.sql", postJpa.getById(NON_EXISTING_POST_ID));
        assertNotNull("Post with id " + EXISTING_POST_ID + " should exist from init.sql", postJpa.getById(EXISTING_POST_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Post post = postJpa.getById(EXISTING_POST_ID);
        String newContents = "new test contents from the test itself";

        assertNotEquals("Post should not have contents newContents by default", newContents, post.getContents());

        post.setContents(newContents);
        postJpa.update(post);

        post = postJpa.getById(EXISTING_POST_ID);
        assertEquals("Post should have contents newContents after update", newContents, post.getContents());
    }

    @Test
    public void testDeletePostById() throws Exception {
        assertNotEquals("Post should exist before deletion", null, postJpa.getById(EXISTING_POST_ID));

        postJpa.deleteById(EXISTING_POST_ID);
        assertEquals("Post should not exist after deletion", null, postJpa.getById(EXISTING_POST_ID));
    }

    @Test
    public void testDeletePostByObject() throws Exception {
        Post post = postJpa.getById(EXISTING_POST_ID);

        assertNotEquals("Post should exist before deletion", null, postJpa.getById(post.getId()));
        postJpa.deleteByObject(post);
        assertEquals("Post should not exist after deletion", null, postJpa.getById(post.getId()));
    }

    @Test
    public void testPersistPost() throws Exception {
        Post post = PostTestHelper.getLegalPost();

        assertFalse("Post should not have id prior to persisting", 0 < post.getId());
        postJpa.persist(post);
        assertTrue("Post should not have id post persisting", 0 < post.getId());
    }

    @Test
    public void testClose() throws Exception {
        assertTrue("Before closing, persister should be open", getPersister().isOpen());
        postJpa.close();
        assertFalse("After closing, persister should not be open", getPersister().isOpen());
    }

    @Test
    public void testPostUpdate() throws Exception {
        Post post = getPersister().createNamedQuery(Post.POST_ALL, Post.class).getResultList().get(0);
        int postID = post.getId();
        String newTitle = "This is a new title for this post";

        post.setTitle(newTitle);
        postJpa.update(post);
        post = postJpa.getById(postID);

        assertEquals("post should have persisted new title in update", newTitle, post.getTitle());
    }

    @Test
    public void testGetTodaysPosts() throws Exception {
        assertFalse("No post should be posted as of today in init.sql", 0 < postJpa.getTodaysPosts().size());
        Post p = PostTestHelper.getLegalPost();
        getPersister().getTransaction().begin();

        postJpa.persist(p);

        assertTrue("After persisting a new post, todays post should contain a result", 0 < postJpa.getTodaysPosts().size());
        getPersister().getTransaction().rollback();
    }
}
