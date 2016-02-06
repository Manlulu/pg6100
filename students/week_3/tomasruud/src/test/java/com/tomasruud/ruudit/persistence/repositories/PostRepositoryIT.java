package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.cases.EntityManagerTestCase;
import com.tomasruud.ruudit.models.Post;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PostRepositoryIT extends EntityManagerTestCase<PostRepository> {

    public PostRepositoryIT() {

        super( PostRepository.class );
    }

    @Test
    public void getAllReturnsAllRows() throws Exception {

        assertTrue( getPersistence().getAll().size() > 0 );
    }

    @Test
    public void getSingleReturnsSingle() throws Exception {

        Post post = getPersistence().get( getPersistence().getAll().iterator().next().getId() );

        assertNotNull( post );
    }

    @Test
    public void createPostCreatesNewPost() throws Exception {

        Post post = new Post();
        post.setContent( "Something" );
        post.setTitle( "Something" );

        int initialCount = getPersistence().getAll().size();

        getPersistence().create( post );

        assertNotNull( post.getId() );
        assertNotNull( "Created date was not automatically generated!", post.getCreated() );

        assertEquals( initialCount + 1, getPersistence().getAll().size() );
    }

    @Test
    public void updatePostUpdatesPost() throws Exception {

        Post post = getPersistence().getAll().iterator().next();

        String initialTitle = post.getTitle();
        String newTitle = initialTitle + " not anymore";

        post.setTitle( newTitle );

        getPersistence().update( post );

        assertNotEquals( initialTitle, newTitle );
    }

    @Test
    public void destroyPostRemovesThePost() throws Exception {

        List<Post> all = getPersistence().getAll();
        int count = all.size();

        getPersistence().destroy( all.iterator().next() );

        assertNotEquals( count, getPersistence().getAll().size() );
    }

    @Test
    public void getAllReturnsStuffInChronologicalOrder() throws Exception {

        List<Post> all = getPersistence().getAll();

        assertTrue( all.size() > 1 );

        Post first = all.get( 0 );
        Post second = all.get( 1 );

        assertTrue( first.getCreated().after( second.getCreated() ) );
    }
}