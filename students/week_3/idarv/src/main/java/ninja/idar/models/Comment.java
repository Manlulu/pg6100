package ninja.idar.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(query = "SELECT e FROM Comment e", name = Comment.COMMENT_ALL)
})
public class Comment {
    public static final String COMMENT_ALL = "allComments";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    @Size(min=3, max = 1000)
    private String contents;


    public Comment() {
    }

    public Comment(String contents) {
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                '}';
    }
}
