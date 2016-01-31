package no.espenschatten.exercise.hackernews.comment.boundary;

import no.espenschatten.exercise.hackernews.comment.controller.CommentController;
import no.espenschatten.exercise.hackernews.util.controller.ReputationController;

import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class CommentBoundary {

	@Inject
	CommentController commentController;

	@Inject
	ReputationController reputationController;

	 public void putComment(int userId, int newsId, String text) {
		 commentController.createNewComment(userId, newsId, text);

	 }

	public void voteUpComment(int commentId) {
		reputationController.increaseReputation(commentController.findCommentById(commentId));
	}

	public void voteDownComment(int commentId) {
		reputationController.decreaseReputation(commentController.findCommentById(commentId));
	}

	public List getCommentsByUserId(int userId) {
		return commentController.findCommentsByUserId(userId);
	}

	public List getCommentsByPostId(int postId) {
		return commentController.findCommentsByPostId(postId);
	}
}
