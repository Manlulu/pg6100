package com.tomasruud.ruudit.controllers;

import com.tomasruud.ruudit.helpers.Flash;
import com.tomasruud.ruudit.models.Post;
import com.tomasruud.ruudit.models.User;
import com.tomasruud.ruudit.models.VoteCollection;
import com.tomasruud.ruudit.persistence.repositories.PostRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
public class PostController {

    private Post post;

    @Inject
    private PostRepository repository;

    @PostConstruct
    public void setup() {

        post = new Post();
    }

    public String create( User creator ) {

        post.setCreator( creator );
        post.upvote( creator );

        Post newPost = repository.create( post );

        if( newPost == null )
            return "";

        Flash.showSuccess( "Post successfully created!" );
        return "/index.xhtml";
    }

    public void upvote( Post post, User user ) {

        if( user == null )
            return;

        post.upvote( user );
        repository.update( post );
    }

    public void downvote( Post post, User user ) {

        if( user == null )
            return;

        post.downvote( user );
        repository.update( post );
    }

    public boolean userHasUpvoted( Post post, User user ) {

        Boolean vote = post.getVotes().getVotes().get( user );

        if( vote == null )
            return false;

        return vote == VoteCollection.UPVOTE;
    }

    public boolean userHasDownvoted( Post post, User user ) {

        if( user == null )
            return false;

        Boolean vote = post.getVotes().getVotes().get( user );

        if( vote == null )
            return false;

        return vote == VoteCollection.DOWNVOTE;
    }

    public List<Post> getAllPosts() {

        List<Post> posts = repository.getAll();

        return posts;
    }

    public Post getPost() {

        return post;
    }

    public void setPost( Post post ) {

        this.post = post;
    }

    public PostRepository getRepository() {

        return repository;
    }

    public void setRepository( PostRepository repository ) {

        this.repository = repository;
    }
}
