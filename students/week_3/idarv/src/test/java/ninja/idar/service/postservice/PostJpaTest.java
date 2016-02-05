package ninja.idar.service.postservice;

import ninja.idar.helpers.GenericBeanIntegrationTestHelper;
import ninja.idar.helpers.PostTestHelper;
import ninja.idar.models.Post;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public class PostJpaTest extends GenericBeanIntegrationTestHelper<Post>{
    private PostJPA postJPA;
    private static final int EXISTING_POST_ID = 1001;
    private static final int NON_EXISTING_POST_ID = 654321;

    @Before
    public void setUp() throws Exception {
        postJPA = new PostJPA(getPersister());
    }

    @Test
    public void testGetPosts() throws Exception {
        assertTrue("init.sql should populate users", 0 < postJPA.getAll().size());
    }

    @Test
    public void testGetPostById() throws Exception {
        assertNull("Post with id " + NON_EXISTING_POST_ID + " should not exist from init.sql", postJPA.getById(NON_EXISTING_POST_ID));
        assertNotNull("Post with id " + EXISTING_POST_ID + " should exist from init.sql", postJPA.getById(EXISTING_POST_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Post post = postJPA.getById(EXISTING_POST_ID);
        String newContents = "new test contents from the test itself";

        assertNotEquals("Post should not have contents newContents by default", newContents, post.getContents());

        post.setContents(newContents);
        postJPA.update(post);

        post = postJPA.getById(EXISTING_POST_ID);
        assertEquals("Post should have contents newContents after update", newContents, post.getContents());
    }

    @Test
    public void testDeletePostById() throws Exception {
        assertNotEquals("Post should exist before deletion", null, postJPA.getById(EXISTING_POST_ID));
        postJPA.deleteById(EXISTING_POST_ID);
        assertEquals("Post should not exist after deletion", null, postJPA.getById(EXISTING_POST_ID));
    }

    @Test
    public void testDeletePostByObject() throws Exception {
        Post post = postJPA.getById(EXISTING_POST_ID);
        assertNotEquals("Post should exist before deletion", null, postJPA.getById(post.getId()));
        postJPA.deleteByObject(post);
        assertEquals("Post should not exist after deletion", null, postJPA.getById(post.getId()));
    }

    @Test
    public void testPersistPost() throws Exception {
        Post post = PostTestHelper.getLegalPost();

        assertFalse("Post should not have id prior to persisting", 0 < post.getId());
        postJPA.persist(post);
        assertTrue("Post should not have id post persisting", 0 < post.getId());
    }

    @Test
    public void testClose() throws Exception {
        assertTrue("Before closing, persister should be open", getPersister().isOpen());
        postJPA.close();
        assertFalse("After closing, persister should not be open", getPersister().isOpen());

    }
}
