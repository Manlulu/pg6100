package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.models.Comment;
import com.tomasruud.ruudit.models.Post;
import com.tomasruud.ruudit.models.User;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserRepository extends AbstractEntityManagerRepository<Long, User> {

    public UserRepository() {

        super( User.class );
    }

    @Override
    public boolean destroy( User object ) {

        if( object == null )
            return true;

        // TODO: Try to find a way to do this automatically or whatever
        // It's probably not really a problem though, because you won't really be deleting
        // users too often. At least not at the moment.

        removePostVoteConstraints( object );
        removePostConstraints( object );

        removeCommentVoteConstraints( object );
        removeCommentConstraints( object );

        return super.destroy( object );
    }

    private void removePostVoteConstraints( User object ) {

        List<Post> posts = getManager()
            .createQuery( "SELECT p FROM Post p WHERE KEY( p.votes.votes ) = :user", Post.class )
            .setParameter( "user", object )
            .getResultList();

        if( posts != null )
            posts.forEach( post -> {

                post.getVotes().getVotes().remove( object );
            } );
    }

    private void removePostConstraints( User object ) {

        List<Post> posts = getManager()
                .createQuery( "SELECT p FROM Post p WHERE p.creator = :user", Post.class )
                .setParameter( "user", object )
                .getResultList();

        if( posts != null )
            posts.forEach( post -> {

                post.setCreator( null );
            } );
    }

    private void removeCommentConstraints( User object ) {

        List<Comment> comments = getManager()
                .createQuery( "SELECT c FROM Comment c WHERE c.creator = :user", Comment.class )
                .setParameter( "user", object )
                .getResultList();

        if( comments != null )
            comments.forEach( comment -> {

                comment.setCreator( null );
            } );
    }


    private void removeCommentVoteConstraints( User object ) {

        List<Comment> comments = getManager()
            .createQuery( "SELECT c FROM Comment c WHERE KEY( c.votes.votes ) = :user", Comment.class )
            .setParameter( "user", object )
            .getResultList();

        if( comments != null )
            comments.forEach( comment -> {

                comment.getVotes().getVotes().remove( object );
            } );
    }
}
