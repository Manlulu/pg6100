package ninja.idar.helpers;

import ninja.idar.models.Post;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Idar Vassdal on 05.02.2016.
 */
public class PostTestHelper {

    public static Post getLegalPost(){
        LocalDateTime publishedDate = LocalDateTime.now(ZoneId.systemDefault());
        String postTitle = "Post Title";
        String postContents = "This is a default post comment";

        return new Post(postTitle, postContents, publishedDate);
    }
}
