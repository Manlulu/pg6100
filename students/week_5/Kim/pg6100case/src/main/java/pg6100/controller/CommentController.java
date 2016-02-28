package pg6100.controller;

import pg6100.ejb.comment.CommentEJB;
import pg6100.entity.Comment;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CommentController {

    @EJB
    private CommentEJB commentEJB;


    public String createComment(Comment comment) {
        if (commentEJB.createComment(comment) != null) {
            return "success";
        } else {
            return "failed";
        }
    }

    public List<Comment> getAllComments(){
        return commentEJB.getAllComment();
    }
    public int getNumberOfComments(){
        return getAllComments().size();
    }
}
