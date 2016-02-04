package pg6100.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name = News.query_getNewsById, query = "SELECT n FROM News n WHERE n.newsId = :id"),
        @NamedQuery(name = News.query_getAllNews, query = "SELECT n FROM News n"),
        @NamedQuery(name = News.query_getNewsByName, query = "SELECT n FROM News n WHERE n.name = :name")
})
public class News {

    public static final String query_getNewsById = "news.getNewsById";
    public static final String query_getAllNews = "news.getAllNews";
    public static final String query_getNewsByName = "news.getNewsByName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long newsId;
    @NotNull
    @Size(max = 64)
    private String name;

    @Size(max = 150)
    private String article;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_COMMENT")
    private List<Comment> comments;

    private int votes;

    private int rating;

    private Date createdAt;

    private Date updatedAt;

    @Transient
    private Map<User, Integer> userVotes;

    public News() {
        userVotes = new HashMap<>();
    }

    public News(String name, String article) {
        this.name = name;
        this.article = article;
        userVotes = new HashMap<>();
        comments = new ArrayList<>();
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        comments.forEach(c -> c.setNews(this));
        this.comments = comments;
    }

    public Map<User, Integer> getUserVotes() {
        return userVotes;
    }

    public void setUserVotes(Map<User, Integer> userVotes) {
        this.userVotes = userVotes;
    }

    public int getVotes() {
        votes = 0;
        for (Map.Entry<User, Integer> userVote : userVotes.entrySet()) {
            votes += userVote.getValue();
        }
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    // Make more sophisticated. Add date in the mix.
    public int getRating() {
        return votes + getComments().size();
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public void addComment(Comment comment){
        if(!comment.isCommentSet()) {
            List<Comment> commentList = getComments();
            commentList.add(comment);
            comment.setCommentSet(true);
        }
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
}
