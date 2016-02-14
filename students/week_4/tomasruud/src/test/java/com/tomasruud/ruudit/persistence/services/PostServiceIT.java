package com.tomasruud.ruudit.persistence.services;

import com.tomasruud.ruudit.cases.ContainerTestCase;
import com.tomasruud.ruudit.models.Post;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PostServiceIT extends ContainerTestCase {

    private PostService service;

    @Before
    public void setRepository() throws Exception {

        service = getManagedClass( PostService.class );
    }

    @Test
    public void getUpvotesReturnsCorrectNumber() throws Exception {

        int upvotes = service.getUpvotes( 70L );

        assertEquals( 2, upvotes );
    }

    @Test
    public void getDownvotesReturnsCorrectNumber() throws Exception {

        int downvotes = service.getDownvotes( 70L );

        assertEquals( 1, downvotes );
    }

    @Test
    public void getSortedByUpvotesDoesWhatIExpectItToDo() throws Exception {

        List<Post> posts = service.getAllSortedByUpvotes();

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