package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.cases.ContainerTestCase;
import com.tomasruud.ruudit.models.Post;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PostRepositoryIT extends ContainerTestCase {

    private PostRepository repository;

    @Before
    public void setRepository() throws Exception {

        repository = getManagedClass( PostRepository.class );
    }

    @Test
    public void getAllReturnsAllRows() throws Exception {

        assertTrue( repository.getAll().size() > 0 );
    }

    @Test
    public void getSingleReturnsSingle() throws Exception {

        Post post = repository.get( repository.getAll().iterator().next().getId() );

        assertNotNull( post );
    }

    @Test
    public void createPostCreatesNewPost() throws Exception {

        Post post = new Post();
        post.setContent( "Something" );
        post.setTitle( "Something" );

        int initialCount = repository.getAll().size();

        repository.create( post );

        assertNotNull( post.getId() );
        assertNotNull( "Created date was not automatically generated!", post.getCreated() );

        assertEquals( initialCount + 1, repository.getAll().size() );
    }

    @Test
    public void updatePostUpdatesPost() throws Exception {

        Post post = repository.getAll().iterator().next();

        String initialTitle = post.getTitle();
        String newTitle = initialTitle + " not anymore";

        post.setTitle( newTitle );

        repository.update( post );

        assertNotEquals( initialTitle, newTitle );
    }

    @Test
    public void destroyPostRemovesThePost() throws Exception {

        List<Post> all = repository.getAll();
        int count = all.size();

        repository.destroy( all.iterator().next() );

        assertNotEquals( count, repository.getAll().size() );
    }

    @Test
    public void getAllReturnsStuffInChronologicalOrder() throws Exception {

        List<Post> all = repository.getAll();

        assertTrue( all.size() > 1 );

        Post first = all.get( 0 );
        Post second = all.get( 1 );

        assertTrue( first.getCreated().after( second.getCreated() ) );
    }
}