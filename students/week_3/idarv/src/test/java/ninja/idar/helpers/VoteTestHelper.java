package ninja.idar.helpers;

import ninja.idar.models.Post;
import ninja.idar.models.User;
import ninja.idar.models.Vote;

/**
 * Created by Idar Vassdal on 05.02.2016.
 */
public class VoteTestHelper {
    public static final int EXISTING_VOTE_ID = 1001;

    public static Vote getLegalVote() {
        Post votePost = PostTestHelper.getLegalPost();
        User voteUser = UserTestHelper.getLegalUser();
        int vote = 1;

        return new Vote(voteUser, votePost, vote);
    }
}
