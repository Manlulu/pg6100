package ninja.idar.models;

import ninja.idar.helpers.GenericBeanValidationTestHelper;
import ninja.idar.helpers.PostTestHelper;
import ninja.idar.helpers.UserTestHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class VoteValidationTest extends GenericBeanValidationTestHelper<Vote> {
    private Vote vote;

    @Before
    public void setUp() throws Exception {
        initLegalVote();
    }

    @Test
    public void testEmptyVote() throws Exception {
        vote = new Vote();
        assertFalse("empty vote should not be valid", isValid(vote));
    }

    @Test
    public void testLegalNote() throws Exception {
        assertTrue("empty vote should not be valid", isValid(vote));
    }

    @Test
    public void testNoteValidations() throws Exception {
        vote.setComment(null);
        vote.setPost(null);
        assertFalse("Without post or comment, vote is not valid", isValid(vote));

        vote.setPost(PostTestHelper.getLegalPost());
        assertTrue("With post set, vote is valid", isValid(vote));

        vote.setComment(new Comment("comment"));
        vote.setPost(PostTestHelper.getLegalPost());
        assertFalse("With both a comment and a post, vote is not valid", isValid(vote));
    }

    private void initLegalVote() {
        User user = UserTestHelper.getLegalUser();
        Comment comment = new Comment("comment");
        vote = new Vote(user, comment, Vote.VOTE_UP);
    }
}
