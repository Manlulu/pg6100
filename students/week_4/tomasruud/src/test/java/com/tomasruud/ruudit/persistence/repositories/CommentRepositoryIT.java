package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.cases.ContainerTestCase;
import com.tomasruud.ruudit.models.Comment;
import com.tomasruud.ruudit.models.Post;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommentRepositoryIT extends ContainerTestCase {

    private CommentRepository repository;

    @Before
    public void setRepository() throws Exception {

        repository = getManagedClass( CommentRepository.class );
    }

    @Test
    public void getAllReturnsAll() throws Exception {

        assertTrue( repository.getAll().size() > 0 );
    }

    @Test
    public void getOneReturnsOne() throws Exception {

        Comment comment = repository.getAll().iterator().next();

        assertNotNull( repository.get( comment.getId() ) );
    }

    @Test
    public void createOneCreatesOne() throws Exception {

        Comment comment = new Comment();
        Post post = new Post();
        post.setId( 70L );

        comment.setContent( "Something" );
        comment.setParentPost( post );

        repository.create( comment );

        assertNotNull( comment.getId() );
        assertNotNull( comment.getCreated() );
    }

    @Test
    public void updateUpdates() throws Exception {

        Comment comment = repository.getAll().iterator().next();

        String content = comment.getContent();

        comment.setContent( content + " something" );

        assertNotEquals( content, repository.get( comment.getId() ) );
    }
    
    @Test
    public void removeRemovesEntity() throws Exception {

        Comment comment = repository.getAll().iterator().next();
        int count = repository.getAll().size();

        repository.destroy( comment );

        assertEquals( count - 1, repository.getAll().size() );
    }
}