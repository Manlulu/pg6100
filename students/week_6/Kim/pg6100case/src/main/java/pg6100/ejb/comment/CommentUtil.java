package pg6100.ejb.comment;

import pg6100.entity.Comment;
import pg6100.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Map;

@Stateless
public class CommentUtil {

    @Inject
    private CommentEJB commentEJB;

    private Map<User, Integer> votes;

    protected void setCommentEJB(CommentEJB commentEJB){
        this.commentEJB = commentEJB;
    }

    public void upVote(User user, Comment comment) {
        votes = comment.getUserVotes();
        if (votes.containsKey(user) && votes.get(user) == 1) {
            votes.remove(user);
        } else {
            votes.put(user, 1);
        }
        comment.setUserVotes(votes);
        commentEJB.updateComment(comment);
    }

    public void downVote(User user, Comment comment) {
        votes = comment.getUserVotes();
        if (votes.containsKey(user) && votes.get(user) == -1) {
            votes.remove(user);
        } else {
            votes.put(user, -1);
        }
        comment.setUserVotes(votes);
        commentEJB.updateComment(comment);
    }
// push

}
