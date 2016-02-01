package ninja.idar.models;

import ninja.idar.helpers.GenericBeanIntegrationTestHelper;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class VoteIT extends GenericBeanIntegrationTestHelper<Vote> {
    private Vote vote;

    @AfterClass
    public static void afterTests(){
        closePersister();
    }

    @Test
    public void testUserTableIsPopulated() throws Exception {
        assertTrue("Vote table should be populated from init.sql", isTablePopulated("Vote"));
    }

    @Test
    public void testNamedQueries() throws Exception {
        // Not implemented
    }

    @Test
    public void testPersist() throws Exception {
        vote = new Vote();
        User user = getFirstUserFromPersister();
        Comment comment = getFirstCommentFromPersister();

        vote.setUser(user);
        vote.setComment(comment);
        vote.setVote(Vote.VOTE_UP);

        assertFalse(vote.getId() > 0);
        persistEntity(vote);
        assertTrue(vote.getId() > 0);
    }

    private User getFirstUserFromPersister(){
     return getPersister().createQuery("SELECT e FROM User e", User.class).setMaxResults(1).getResultList().get(0);
    }

    private Comment getFirstCommentFromPersister(){
        return getPersister().createQuery("SELECT e FROM Comment e", Comment.class).setMaxResults(1).getResultList().get(0);
    }
}
