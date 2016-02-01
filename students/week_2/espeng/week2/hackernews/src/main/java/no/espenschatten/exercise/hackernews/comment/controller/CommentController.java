package no.espenschatten.exercise.hackernews.comment.controller;

import no.espenschatten.exercise.hackernews.comment.entity.Comment;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CommentController {

	@PersistenceContext(name = "hackernews")
	private EntityManager entityManager;

	public List findCommentsByUserId(int userId) {
		Query q = entityManager.createNativeQuery(Comment.FIND_COMMENTS_BY_USER_ID, Comment.class);
		q.setParameter(userId, "userId");
		return q.getResultList();
	}

	/**
	 *
	 * @param commentId commentId for the comment we want to get. Will be used as parameter to the named query.
	 * @return not using getSingleResult() - because I don't like catching exceptions for no results..
	 */
	public Comment findCommentById(int commentId) {
		Query q = entityManager.createNativeQuery(Comment.FIND_COMMENT_BY_COMMENT_ID, Comment.class);
		q.setParameter(commentId, "commentId");
		List results = q.getResultList();

		return (results.isEmpty()) ? new Comment() : (Comment) results.get(0);
	}

	public List findCommentsByPostId(int postId) {
		Query q = entityManager.createNativeQuery(Comment.FIND_COMMENTS_BY_POST_ID, Comment.class);
		q.setParameter(postId, "postId");
		return q.getResultList();
	}

	public void createNewComment(int userId, int postId, String text) {
		Comment comment = new Comment(userId, postId, text);
		entityManager.persist(comment);
	}
}
