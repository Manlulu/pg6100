package pg6100.repository.Comment;


import pg6100.domain.Comment;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
public class CommentStatistics {

    @Inject
    private JpaCommentDao jpaCommentDao;

    protected void setJpaCommentDao(JpaCommentDao jpaCommentDao){
        this.jpaCommentDao = jpaCommentDao;
    }

    public int getTotalNumberOfComments(){
        return jpaCommentDao.getAllComment().size();
    }

    public int getNumberOfCommentsThisDay() {
        List<Comment> commentList = jpaCommentDao.getAllComment();

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
