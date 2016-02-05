package ninja.idar.models;

import ninja.idar.constraints.converters.LocalDateAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by Idar Vassdal on 25.01.2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(query = "SELECT e FROM Post e", name = Post.POST_ALL),
        @NamedQuery(query = "SELECT e FROM Post e ORDER BY publishedDate DESC", name = Post.POST_ALL_BY_DATE),
        @NamedQuery(query = "SELECT COUNT(*) FROM Post", name = Post.POST_ALL_COUNT)
})
public class Post {
    public static final String POST_ALL_COUNT = "allPostsCount";
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
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDateTime publishedDate;


    public Post() {
    }

    public Post(String title, String contents, LocalDateTime publishedDate) {
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

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
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
