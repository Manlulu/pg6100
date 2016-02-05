package ninja.idar.service.voteservice;

import ninja.idar.helpers.GenericBeanIntegrationTestHelper;
import ninja.idar.helpers.VoteTestHelper;
import ninja.idar.models.Vote;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Idar Vassdal on 05.02.2016.
 */
public class VoteJpaTest extends GenericBeanIntegrationTestHelper<VoteJpa> {
    private VoteJpa voteJpa;

    @Before
    public void setUp() throws Exception {
        voteJpa = new VoteJpa(getPersister());
    }

    @Test
    public void testGetAll() throws Exception {
        assertTrue("init.sql should populate votes", 0 < voteJpa.getAll().size());
    }

    @Test
    public void testUpdate() throws Exception {
        Vote vote = getPersister().createNamedQuery(Vote.VOTE_ALL, Vote.class).getResultList().get(0);
        int voteId = vote.getId();
        int newVote = vote.getVote() == 1 ? -1 : 1;
        vote.setVote(newVote);
        voteJpa.update(vote);
        vote = voteJpa.getById(voteId);
        assertEquals("Vote should have new vote-score after updating", newVote, vote.getVote());
    }

    @Test
    public void testGetById() throws Exception {
        assertTrue("getbyid should return vote, because it's populated in init.sql", voteJpa.getById(VoteTestHelper.EXISTING_VOTE_ID) != null);
    }

    @Test
    public void testDeleteById() throws Exception {
        assertNotEquals("Vote should exist before deletion", null, voteJpa.getById(VoteTestHelper.EXISTING_VOTE_ID));
        voteJpa.deleteById(VoteTestHelper.EXISTING_VOTE_ID);
        assertEquals("Vote should not exist after deletion", null, voteJpa.getById(VoteTestHelper.EXISTING_VOTE_ID));

    }

    @Test
    public void testDeleteByObject() throws Exception {
        Vote vote = voteJpa.getById(VoteTestHelper.EXISTING_VOTE_ID);
        assertNotEquals("Vote should exist before deletion", null, voteJpa.getById(vote.getId()));
        voteJpa.deleteByObject(vote);
        assertEquals("Vote should not exist after deletion", null, voteJpa.getById(vote.getId()));
    }

    @Test
    public void testPersist() throws Exception {
        Vote vote = VoteTestHelper.getLegalVote();
        getPersister().persist(vote.getUser());
        getPersister().persist(vote.getPost());
        assertFalse("Vote should not have ID if not persisted", 0 < vote.getId());
        voteJpa.persist(vote);
        assertTrue("Vote should have ID when persisted", 0 < vote.getId());
    }

    @Test
    public void testClose() throws Exception {
        assertTrue("Before closing, persister should be open", getPersister().isOpen());
        voteJpa.close();
        assertFalse("After closing, persister should not be open", getPersister().isOpen());
    }
}
