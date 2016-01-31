package no.ingesen.martin.ejb;

import no.ingesen.martin.entity.Vote;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class VoteBeanIT extends ITBase {

    @Test
    public void testPersist() throws Exception {
        Vote vote = new Vote();

        vote.setType(Vote.Type.UP);
        vote.setPost(postBean.findById(1));
        vote.setComment(null);
        vote.setUser(userBean.findById(1));

        entityManager.getTransaction().begin();
        voteBean.persist(vote);
        entityManager.getTransaction().commit();

        assertTrue(vote.getId() > 0);
    }

    @Test
    public void testFindById() throws Exception {
        Vote vote = voteBean.findById(1);
        assertTrue(vote.getId() == 1);
    }

}
