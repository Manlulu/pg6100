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

        assertTrue( getRepository().getAll().size() > 0 );
    }

    @Test
    public void getSingleReturnsSingle() throws Exception {

        Post post = getRepository().get( getRepository().getAll().iterator().next().getId() );

        assertNotNull( post );
    }

    @Test
    public void createPostCreatesNewPost() throws Exception {

        Post post = new Post();
        post.setContent( "Something" );
        post.setTitle( "Something" );

        int initialCount = getRepository().getAll().size();

        getRepository().create( post );

        assertNotNull( post.getId() );
        assertNotNull( "Created date was not automatically generated!", post.getCreated() );

        assertEquals( initialCount + 1, getRepository().getAll().size() );
    }

    @Test
    public void updatePostUpdatesPost() throws Exception {

        Post post = getRepository().getAll().iterator().next();

        String initialTitle = post.getTitle();
        String newTitle = initialTitle + " not anymore";

        post.setTitle( newTitle );

        getRepository().update( post );

        assertNotEquals( initialTitle, newTitle );
    }

    @Test
    public void destroyPostRemovesThePost() throws Exception {

        List<Post> all = getRepository().getAll();
        int count = all.size();

        getRepository().destroy( all.iterator().next() );

        assertNotEquals( count, getRepository().getAll().size() );
    }
}