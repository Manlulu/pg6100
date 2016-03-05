package pg6100.entity;

import pg6100.entity.enums.NewsListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@EntityListeners(NewsListener.class)
@NamedQueries({
        @NamedQuery(name = News.query_getNewsById, query = "SELECT n FROM News n WHERE n.newsId = :id"),
        @NamedQuery(name = News.query_getAllNews, query = "SELECT n FROM News n"),
        @NamedQuery(name = News.query_getNewsByName, query = "SELECT n FROM News n WHERE n.name = :name")
})
public class News {

    public static final String query_getNewsById = "News.getNewsById";
    public static final String query_getAllNews = "News.getAllNews";
    public static final String query_getNewsByName = "News.getNewsByName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long newsId;
    @NotNull
    @Size(max = 64)
    private String name;

    @Size(max = 150)
    private String article;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE} , fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_COMMENT")
    private List<Comment> comments;

    private int votes;

    private int rating;
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    private User user;


    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_upvote")
    private List<User> usersUpVote;

    public List<User> getUsersDownVote() {
        return usersDownVote;
    }

    public void setUsersDownVote(List<User> users) {
        int votes = getVotes();
        if (users != null && users.size() > 0) {


            List<News> news = new ArrayList<>();

            news.add(this);

            for (int i = 0; i < users.size(); i++) {

                users.get(i).setNews(news);
                votes--;
            }


        }
        setVotes(votes);
        this.usersDownVote = users;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_downvote")
    private List<User> usersDownVote;

    public News() {
        this.setCreatedAt(new Date());
    }

    public News(String name, String article) {
        this.name = name;
        this.article = article;
        comments = new ArrayList<>();
    }

    public News(String name, String article, User user) {
        this.name = name;
        this.article = article;
        this.user = user;
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

    public List<User> getUsersUpVote() {
        return usersUpVote;
    }

    public void setUsersUpVote(List<User> users) {
        int votes = getVotes();
        if (users != null && users.size() > 0) {
            List<News> news = new ArrayList<>();
            news.add(this);
            for (int i = 0; i < users.size(); i++) {
                users.get(i).setNews(news);
                votes++;
            }
        }
        setVotes(votes);
        this.usersUpVote = users;
    }

    public int getVotes() {
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
            if(commentList == null){
                commentList = new ArrayList<>();
            }
            commentList.add(comment);
            comment.setCommentSet(true);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @PrePersist
    private void createdAt(){
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != News.class) return false;
        if(o == this) return true;

        News other = (News)o;
        return other.getNewsId() == this.getNewsId();

    }
}
