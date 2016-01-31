package no.ingesen.martin.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(name = "SEQ_VOTE", initialValue = 100)
public class Vote {

    public enum Type{
        UP, DOWN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VOTE")
    private int id;

    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_USER")
    private User user;

    @Valid
    @ManyToOne
    @JoinColumn(name = "FK_POST")
    private Post post;

    @Valid
    @ManyToOne
    @JoinColumn(name = "FK_COMMENT")
    private Comment comment;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
