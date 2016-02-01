package ninja.idar.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Idar Vassdal on 25.01.2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(query = "SELECT e FROM Post e", name = Post.POST_ALL)
})
public class Post {
    public static final String POST_ALL = "allPosts";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    @Size(min = 5, max = 30)
    private String title;

    @NotNull
    @Size(min = 20, max = 1000)
    private String contents;

    public Post() {
    }

    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
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
}
