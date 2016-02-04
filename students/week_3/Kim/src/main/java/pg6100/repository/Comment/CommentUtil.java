package pg6100.repository.Comment;


import pg6100.domain.Comment;
import pg6100.domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Map;

@Stateless
public class CommentUtil {

    @Inject
    private JpaCommentDao jpaCommentDao;

    private Map<User, Integer> votes;

    protected void setJpaCommentDao(JpaCommentDao jpaCommentDao){
        this.jpaCommentDao = jpaCommentDao;
    }

    public void upVote(User user, Comment comment) {
        votes = comment.getUserVotes();
        if (votes.containsKey(user) && votes.get(user) == 1) {
            votes.remove(user);
        } else {
            votes.put(user, 1);
        }
        comment.setUserVotes(votes);
        jpaCommentDao.updateComment(comment);
    }

    public void downVote(User user, Comment comment) {
        votes = comment.getUserVotes();
        if (votes.containsKey(user) && votes.get(user) == -1) {
            votes.remove(user);
        } else {
            votes.put(user, -1);
        }
        comment.setUserVotes(votes);
        jpaCommentDao.updateComment(comment);
    }


}
