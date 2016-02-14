package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.models.Post;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PostRepository extends AbstractEntityManagerRepository<Long, Post> {

    public PostRepository() {

        super( Post.class );
    }

    @Override
    public List<Post> getAll() {

        try {

            TypedQuery<Post> query = getManager().createQuery(
                    "SELECT p FROM Post p ORDER BY p.created DESC", Post.class );

            return query.getResultList();

        } catch( Exception e ) { return null; }
    }
}
