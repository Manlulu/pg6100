package no.espenschatten.exercise.hackernews.comment.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@NamedQueries(
		value = {
				@NamedQuery(name = Comment.FIND_COMMENTS_BY_USER_ID, query = "SELECT * FROM COMMENTS WHERE userId = :userId"),
				@NamedQuery(name = Comment.FIND_COMMENT_BY_COMMENT_ID, query = "SELECT * FROM COMMENTS WHERE commentid = :commentId"),
				@NamedQuery(name = Comment.FIND_COMMENTS_BY_POST_ID, query = "SELECT * FROM COMMENTS WHERE postId = :postId")
		}
)
@Entity
public class Comment {

	public static final String FIND_COMMENTS_BY_USER_ID = "findCommentsByUserId";
	public static final String FIND_COMMENTS_BY_POST_ID = "findCommentsByPostId";
	public static final String FIND_COMMENT_BY_COMMENT_ID = "findCommentByCommentId";

	@Id
	@GeneratedValue
	private int commentId;

	@OneToOne(fetch = FetchType.LAZY)
	private int userId;

	@OneToMany(fetch = FetchType.LAZY)
	private int postId;

	@Size(min = 1, max = 255)
	private String commentText;

	private int votes;

	public Comment() {
	}

	public Comment(int userId, int postId, String commentText) {
		this.userId = userId;
		this.postId = postId;
		this.commentText = commentText;
	}

	public int getPostId() {
		return postId;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

}
