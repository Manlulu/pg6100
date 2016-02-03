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

        assertTrue( getPersistence().getAll().size() > 0 );
    }

    @Test
    public void getOneReturnsOne() throws Exception {

        Comment comment = getPersistence().getAll().iterator().next();

        assertNotNull( getPersistence().get( comment.getId() ) );
    }

    @Test
    public void createOneCreatesOne() throws Exception {

        Comment comment = new Comment();
        Post post = new Post();
        post.setId( 70L );

        comment.setContent( "Something" );
        comment.setParentPost( post );

        getPersistence().create( comment );

        assertNotNull( comment.getId() );
        assertNotNull( comment.getCreated() );
    }

    @Test
    public void updateUpdates() throws Exception {

        Comment comment = getPersistence().getAll().iterator().next();

        String content = comment.getContent();

        comment.setContent( content + " something" );

        assertNotEquals( content, getPersistence().get( comment.getId() ) );
    }
    
    @Test
    public void removeRemovesEntity() throws Exception {

        Comment comment = getPersistence().getAll().iterator().next();
        int count = getPersistence().getAll().size();

        getPersistence().destroy( comment );

        assertEquals( count - 1, getPersistence().getAll().size() );
    }
}