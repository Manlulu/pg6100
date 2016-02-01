package com.tomasruud.ruudit.persistence.services;

import com.tomasruud.ruudit.models.Post;
import com.tomasruud.ruudit.models.User;
import com.tomasruud.ruudit.models.VoteCollection;
import com.tomasruud.ruudit.persistence.repositories.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class PostServiceTest {

    private PostService service;

    @Before
    public void prepareMock() throws Exception {

        PostRepository repository = mock( PostRepository.class );

        HashMap<User, Boolean> voteMap = new HashMap<>();
        voteMap.put( mock( User.class ), VoteCollection.UPVOTE );
        voteMap.put( mock( User.class ), VoteCollection.UPVOTE );
        voteMap.put( mock( User.class ), VoteCollection.DOWNVOTE );

        VoteCollection votes = new VoteCollection();
        votes.setVotes( voteMap );

        Post post = new Post();
        post.setVotes( votes );

        when( repository.get( any() )).thenReturn( post );

        service = new PostService( repository );
    }

    @Test
    public void getUpvotesReturnsCorrectNumber() throws Exception {

        int upvotes = service.getUpvotes( 1L );

        assertEquals( 2, upvotes );
    }

    @Test
    public void getDownvotesReturnsCorrectNumber() throws Exception {

        int downvotes = service.getDownvotes( 1L );

        assertEquals( 1, downvotes );
    }
}