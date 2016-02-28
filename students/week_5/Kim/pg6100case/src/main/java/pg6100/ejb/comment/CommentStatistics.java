package pg6100.ejb.comment;


import pg6100.entity.Comment;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
public class CommentStatistics {

    @Inject
    private CommentEJB commentEJB;

    protected void setCommentEJB(CommentEJB commentEJB){
        this.commentEJB = commentEJB;
    }

    public int getTotalNumberOfComments(){
        return commentEJB.getAllComment().size();
    }

    public int getNumberOfCommentsThisDay() {
        List<Comment> commentList = commentEJB.getAllComment();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        List<Comment> commentsThisDay = new ArrayList<>();

        for (int i = 0; i < commentList.size(); i++) {
            if (commentList.get(i).getCreatedAt().after(today.getTime())) {
                commentsThisDay.add(commentList.get(i));
            }
        }
        return commentsThisDay.size();
    }

    public int getNumberOfCommentsOnOneComment(Comment comment) {
        return comment.getComments().size();
    }

}
