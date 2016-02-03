package com.tomasruud.ruudit.persistence.services;

import com.tomasruud.ruudit.models.Post;
import com.tomasruud.ruudit.persistence.contracts.EntityManagerManaged;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Stateless
public class PostService implements EntityManagerManaged {

    @Inject
    private EntityManager manager;

    public int getUpvotes( Long id ) {

        Post post = findById( id );

        try { return post.getVotes().getUpvotes(); } catch( NullPointerException ignore ) {}

        return 0;
    }

    public int getDownvotes( Long id ) {

        Post post = findById( id );

        try { return post.getVotes().getDownvotes(); } catch( NullPointerException ignore ) {}

        return 0;
    }

    // TODO: Implement this so it only returns posts from today
    // I'll skip this for now, because there's not really enough
    // posts to do anything.
    public List<Post> getAllSortedByUpvotes() {

        List<Post> posts = manager.createQuery( "SELECT p FROM Post p", Post.class ).getResultList();

        // TODO: Sort this in the query
        Comparator<Post> upvoteComparator = ( p1, p2 ) -> {

            try {

                return Integer.compare( p2.getVotes().getUpvotes(), p1.getVotes().getUpvotes() );

            } catch( NullPointerException ignore ) {}

            return -1;
        };

        Collections.sort( posts, upvoteComparator );

        return posts;
    }

    @Override
    public void setManager( EntityManager manager ) {

        this.manager = manager;
    }

    private Post findById( Long id ) {

        return manager.createQuery( "SELECT p FROM Post p WHERE p.id = :id", Post.class ).
                setParameter( "id", id ).
                getSingleResult();
    }
}
