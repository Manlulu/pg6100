package ninja.idar.models;

import ninja.idar.helpers.GenericBeanIntegrationTestHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Idar Vassdal on 25.01.2016.
 */
public class PostIT extends GenericBeanIntegrationTestHelper<Post> {
    private Post post;

    @Before
    public void setUp() throws Exception {
        post = new Post();
    }

    @AfterClass
    public static void afterTests(){
        closePersister();
    }

    @Test
    public void testPostTableIsPopulated() throws Exception {
        assertTrue(isTablePopulated("Post"));
    }

    @Test
    public void testPostNamedQueries() throws Exception {
        assertTrue("GET ALL query should return results because init.sql has run",
                getPersister().createNamedQuery(Post.POST_ALL).getResultList().size() > 0);

        List<Post> postsByDate = getPersister().createNamedQuery(Post.POST_ALL_BY_DATE, Post.class).getResultList();
        assertTrue("GET ALL BY DATE should return results because init.sql has run",
                postsByDate.size() > 0);

        Date postDate = null;
        for(Post p : postsByDate){
            if(postDate == null){
                postDate = p.getPublishedDate();
            } else{
                assertTrue("Each sorted post should be newer than the one before it", postDate.after(p.getPublishedDate()));
                postDate =  p.getPublishedDate();
            }
        }
    }

    @Test
    public void testPostPersist() throws Exception {
        Date date = new Date();
        post = new Post("postTitle", "This is my post and I would like you to read it please", date);
        assertFalse(post.getId() > 0);
        persistEntity(post);
        assertTrue(post.getId() > 0);
    }
}