package no.ingesen.martin.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@SequenceGenerator(name = "SEQ_POST", initialValue = 100)
public class Post {

    public Post() {
    }

    public enum Type{
        LINK, TEXT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POST")
    private int id;

    @NotNull
    @Size(min = 5, max = 75)
    private String title;

    @NotNull
    @Size(max = 250)
    private String content;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_POSTER")
    private User poster;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @NotNull
    private LocalDateTime posted;

    @OneToMany(mappedBy = "post")
    private List<Vote> votes;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
