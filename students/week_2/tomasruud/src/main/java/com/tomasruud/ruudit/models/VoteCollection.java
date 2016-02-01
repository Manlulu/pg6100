package com.tomasruud.ruudit.models;

import javax.persistence.*;
import java.util.Collections;
import java.util.Map;

@Embeddable
public class VoteCollection {

    public static final boolean UPVOTE = true;
    public static final boolean DOWNVOTE = false;

    @ElementCollection
    @MapKeyJoinColumn( name= "user_id" )
    @Column( name = "upvote" )
    private Map<User, Boolean> votes;

    public void upvote( User user ) {

        votes.put( user, UPVOTE );
    }

    public void downvote( User user ) {

        votes.put( user, DOWNVOTE );
    }

    public int getUpvotes() {

        return Collections.frequency( votes.values(), UPVOTE );
    }

    public int getDownvotes() {

        return Collections.frequency( votes.values(), DOWNVOTE );
    }

    public Map<User, Boolean> getVotes() {

        return votes;
    }

    public void setVotes( Map<User, Boolean> votes ) {

        this.votes = votes;
    }
}
