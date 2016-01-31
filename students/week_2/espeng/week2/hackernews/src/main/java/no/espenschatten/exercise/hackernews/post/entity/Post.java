package no.espenschatten.exercise.hackernews.post.entity;

import no.espenschatten.exercise.hackernews.comment.entity.Comment;
import no.espenschatten.exercise.hackernews.user.entity.User;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post {

	@Id
	@GeneratedValue
	private int postId;

	private String content;
	private List<Comment> comments;
	private int votes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private int userId;

	public Post() {
	}

	public Post(int userId, String content) {
		this.userId = userId;
		this.content = content;
	}

	public int getPostId() {
		return postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(User publisher) {
		this.userId = userId;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
}
