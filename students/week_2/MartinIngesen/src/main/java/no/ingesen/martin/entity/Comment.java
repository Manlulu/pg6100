package no.ingesen.martin.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@SequenceGenerator(name = "SEQ_COMMENT", initialValue = 100)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMMENT")
    private int id;

    @NotNull
    @Size(min = 5, max = 100)
    private String content;

    @ManyToOne
    @JoinColumn(name="FK_PARENT")
    private Comment parent;

    @NotNull
    @ManyToOne
    @Valid
    @JoinColumn(name="FK_COMMENTER", nullable = false)
    private User commenter;

    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn(name="FK_POST", nullable = false)
    private Post post;

    @NotNull
    private LocalDateTime commented;

    @OneToMany(mappedBy = "comment")
    private List<Vote> votes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getCommented() {
        return commented;
    }

    public void setCommented(LocalDateTime commented) {
        this.commented = commented;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
