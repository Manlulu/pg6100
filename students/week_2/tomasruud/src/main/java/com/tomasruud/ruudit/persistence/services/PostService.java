package com.tomasruud.ruudit.persistence.services;

import com.tomasruud.ruudit.models.Post;
import com.tomasruud.ruudit.persistence.repositories.PostRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PostService {

    private PostRepository repository;

    @Inject
    public PostService( PostRepository repository ) {

        this.repository = repository;
    }

    // TODO: Use named queries so that we don't have to fetch all the votes every time

    public int getUpvotes( Long id ) {

        Post post = repository.get( id );

        if( post != null )
            return post.getVotes().getUpvotes();

        return 0;
    }

    public int getDownvotes( Long id ) {

        Post post = repository.get( id );

        if( post != null )
            return post.getVotes().getDownvotes();

        return 0;
    }
}
