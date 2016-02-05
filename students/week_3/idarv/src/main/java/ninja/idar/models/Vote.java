package ninja.idar.models;

import ninja.idar.constraints.validators.ValidVote;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
@Entity
@ValidVote
@NamedQueries({
        @NamedQuery(query = "SELECT e FROM Vote e", name = Vote.VOTE_ALL),
        @NamedQuery(query = "SELECT comment FROM Vote e WHERE COMMENT_ID IS NOT NULL GROUP BY COMMENT_ID ORDER BY SUM(vote) DESC",name = Vote.VOTE_MOST_VOTED_COMMENTS),
        @NamedQuery(query = "SELECT SUM(vote) FROM Vote e WHERE COMMENT_ID LIKE :commentId", name = Vote.VOTE_SUM_VOTES_OF_POST)
})
public class Vote {
    public static final String VOTE_ALL = "voteAll";
    public static final String VOTE_MOST_VOTED_COMMENTS = "voteMostVotedPost";
    public static final String VOTE_SUM_VOTES_OF_POST = "voteSumVotesOfPost";
    public static final int VOTE_UP = 1;
    public static final int VOTE_DOWN = -1;

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

    private int vote;

    public Vote() {
    }

    public Vote(User user, Post post, int vote) {
        this.user = user;
        this.post = post;
        this.vote = vote;
    }

    public Vote(User user, Comment comment, int vote) {
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

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
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

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", post=" + post +
                ", comment=" + comment +
                ", user=" + user +
                ", vote=" + vote +
                '}';
    }
}
