package ninja.idar.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Idar Vassdal on 25.01.2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(query = "SELECT e FROM Post e", name = Post.POST_ALL),
        @NamedQuery(query = "SELECT e FROM Post e ORDER BY publishedDate DESC", name = Post.POST_ALL_BY_DATE)
})
public class Post {
    public static final String POST_ALL = "allPosts";
    public static final String POST_ALL_BY_DATE = "allPostsByDate";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull(message = "Post need to contain a title")
    @Size(min = 5, max = 30)
    private String title;

    @NotNull(message = "Post need to contain content")
    @Size(min = 20, max = 1000)
    private String contents;

    @NotNull(message = "Post need a published date")
    private Date publishedDate;


    public Post() {
    }

    public Post(String title, String contents, Date publishedDate) {
        this.title = title;
        this.contents = contents;
        this.publishedDate = publishedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }
}
