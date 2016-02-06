package com.tomasruud.ruudit.persistence.services;

import com.tomasruud.ruudit.cases.EntityManagerTestCase;
import com.tomasruud.ruudit.models.Post;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PostServiceIT extends EntityManagerTestCase<PostService> {

    public PostServiceIT() {

        super( PostService.class );
    }

    @Test
    public void getUpvotesReturnsCorrectNumber() throws Exception {

        int upvotes = getPersistence().getUpvotes( 70L );

        assertEquals( 2, upvotes );
    }

    @Test
    public void getDownvotesReturnsCorrectNumber() throws Exception {

        int downvotes = getPersistence().getDownvotes( 70L );

        assertEquals( 1, downvotes );
    }

    @Test
    public void getSortedByUpvotesDoesWhatIExpectItToDo() throws Exception {

        List<Post> posts = getPersistence().getAllSortedByUpvotes();

        int prev = -1;

        for( Post post : posts ) {

            if( prev == -1 )
                prev = post.getComments().size();

            if( post.getComments().size() > prev )
                fail( "Stuff is not in order" );

            prev = post.getComments().size();
        }
    }
}