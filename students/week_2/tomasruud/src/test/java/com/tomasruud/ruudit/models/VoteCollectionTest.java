package com.tomasruud.ruudit.models;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class VoteCollectionTest {

    @Test
    public void upvoteUpvotes() throws Exception {

        VoteCollection votes = new VoteCollection();
        User user = new User();

        votes.setVotes( new HashMap<>() );

        assertEquals( 0, votes.getUpvotes() );

        votes.upvote( user );

        assertEquals( votes.getVotes().get( user ), VoteCollection.UPVOTE );

        assertEquals( 1, votes.getUpvotes() );
    }

    @Test
    public void downvoteDownvotes() throws Exception {

        VoteCollection votes = new VoteCollection();
        User user = new User();

        votes.setVotes( new HashMap<>() );

        assertEquals( 0, votes.getDownvotes() );

        votes.downvote( user );

        assertEquals( votes.getVotes().get( user ), VoteCollection.DOWNVOTE );

        assertEquals( 1, votes.getDownvotes() );
    }

    @Test
    public void downvoteTurnsUpvoteIntoDownvote() throws Exception {

        VoteCollection votes = new VoteCollection();
        User user = new User();

        votes.setVotes( new HashMap<>() );

        votes.upvote( user );

        assertEquals( votes.getVotes().get( user ), VoteCollection.UPVOTE );

        votes.downvote( user );

        assertEquals( votes.getVotes().get( user ), VoteCollection.DOWNVOTE );
    }
}