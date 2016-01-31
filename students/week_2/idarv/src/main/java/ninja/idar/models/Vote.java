package ninja.idar.models;

import ninja.idar.constraints.validators.ValidVote;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
@Entity
@ValidVote
public class Vote {
    public static final boolean VOTE_UP = true;
    public static final boolean VOTE_DOWN = false;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private boolean vote;

    public Vote() {
    }

    public Vote(User user, Post post, boolean vote) {
        this.user = user;
        this.post = post;
        this.vote = vote;
    }

    public Vote(User user, Comment comment, boolean vote) {
        this.user = user;
        this.vote = vote;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVoteUp() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
