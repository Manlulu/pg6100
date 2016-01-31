package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.models.Post;

import javax.ejb.Stateless;

@Stateless
public class PostRepository extends AbstractEntityManagerRepository<Long, Post> {

    public PostRepository() {

        super( Post.class );
    }
}
