package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.models.Comment;

import javax.ejb.Stateless;

@Stateless
public class CommentRepository extends AbstractEntityManagerRepository<Long, Comment> {

    public CommentRepository() {

        super( Comment.class );
    }
}
