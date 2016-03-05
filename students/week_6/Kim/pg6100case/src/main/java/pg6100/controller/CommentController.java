package pg6100.controller;

import pg6100.ejb.comment.CommentEJB;
import pg6100.ejb.news.NewsEJB;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.Comment;
import pg6100.entity.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CommentController {

    @EJB
    private CommentEJB commentEJB;
    @EJB
    private NewsEJB newsEJB;
    @EJB
    private UserEJB userEJB;


    public String createComment(Comment comment) {
        if (commentEJB.createComment(comment) != null) {
            return "success";
        } else {
            return "failed";
        }
    }

    private String commentBody;

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public void addComment(String username, Comment comment){
//        initComment();

        User user = userEJB.getUserByUserName(username);

        Comment newComment = new Comment();
        newComment.setUser(user);
        newComment.setBody(commentBody);
        createComment(newComment);

        List<Comment> commentList = comment.getComments();
        commentList.add(newComment);

        commentEJB.updateComment(comment);
    }

    public List<Comment> getAllComments(){
        return commentEJB.getAllComment();
    }
    public int getNumberOfComments(){
        return getAllComments().size();
    }
}
