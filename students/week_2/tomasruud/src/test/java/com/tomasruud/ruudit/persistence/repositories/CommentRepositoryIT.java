package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.cases.EntityManagerTestCase;
import com.tomasruud.ruudit.models.Comment;
import com.tomasruud.ruudit.models.Post;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommentRepositoryIT extends EntityManagerTestCase<CommentRepository> {
    
    public CommentRepositoryIT() {

        super( CommentRepository.class );
    }

    @Test
    public void getAllReturnsAll() throws Exception {

        assertTrue( getRepository().getAll().size() > 0 );
    }

    @Test
    public void getOneReturnsOne() throws Exception {

        Comment comment = getRepository().getAll().iterator().next();

        assertNotNull( getRepository().get( comment.getId() ) );
    }

    @Test
    public void createOneCreatesOne() throws Exception {

        Comment comment = new Comment();
        Post post = new Post();
        post.setId( 70L );

        comment.setContent( "Something" );
        comment.setParentPost( post );

        getRepository().create( comment );

        assertNotNull( comment.getId() );
        assertNotNull( comment.getCreated() );
    }

    @Test
    public void updateUpdates() throws Exception {

        Comment comment = getRepository().getAll().iterator().next();

        String content = comment.getContent();

        comment.setContent( content + " something" );

        assertNotEquals( content, getRepository().get( comment.getId() ) );
    }
    
    @Test
    public void removeRemovesEntity() throws Exception {

        Comment comment = getRepository().getAll().iterator().next();
        int count = getRepository().getAll().size();

        getRepository().destroy( comment );

        assertEquals( count - 1, getRepository().getAll().size() );
    }
}