package pg6100.domain;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
@Stateless
@NamedQueries({
        @NamedQuery(name = Comment.query_getCommentById, query = "SELECT c FROM Comment c WHERE c.commentId = :id"),
        @NamedQuery(name = Comment.query_getAllComments, query = "SELECT c FROM Comment c"),
        @NamedQuery(name = Comment.query_getCommentByName, query = "SELECT c FROM Comment c WHERE c.name = :name")

})
public class Comment {

    public static final String query_getCommentById = "comment.getCommentById";
    public static final String query_getAllComments = "comment.getAllComments";
    public static final String query_getCommentByName = "comment.getCommentByName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;
    @Size(max = 64)
    @NotNull
    private String name;
    @Size(max = 150)
    @NotNull
    private String body;

    @ManyToOne
    private News news;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_COMMENT_COMMENT")
    private List<Comment> comments;

    private int votes;

    private Date createdAt;

    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "FK_USER")
    private User user;

    @Transient
    private Map<User, Integer> userVotes;
    @Transient
    private boolean commentSet;

    public Comment(){
        userVotes = new HashMap<>();
    }

    public Comment(String name, String body) {
        this.name = name;
        this.body = body;
        this.commentSet = false;
        userVotes = new HashMap<>();
        comments = new ArrayList<>();
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Map<User, Integer> getUserVotes() {
        return userVotes;
    }

    public void setUserVotes(Map<User, Integer> userVotes) {
        this.userVotes = userVotes;
    }

    public int getVotes() {
        votes = 0;
        for (Map.Entry<User, Integer> entry : userVotes.entrySet()) {
            votes += entry.getValue();
        }
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        if(!comment.isCommentSet()) {
            List<Comment> commentList = getComments();
            commentList.add(comment);
            comment.commentSet = true;
        }
    }

    public boolean isCommentSet() {
        return commentSet;
    }

    public void setCommentSet(boolean commentSet) {
        this.commentSet = commentSet;
    }

    @PrePersist
    private void createdAt(){
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
    }

    // Not working
    @PostUpdate
    private void updatedAt(){
        this.setUpdatedAt(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return commentId == comment.commentId;

    }
}
