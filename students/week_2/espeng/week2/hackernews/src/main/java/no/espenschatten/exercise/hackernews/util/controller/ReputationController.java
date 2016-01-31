package no.espenschatten.exercise.hackernews.util.controller;

import no.espenschatten.exercise.hackernews.comment.entity.Comment;
import no.espenschatten.exercise.hackernews.post.entity.Post;
import no.espenschatten.exercise.hackernews.util.comparator.ReputationComparator;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ReputationController {

	@PersistenceContext(name = "hackernews")
	private EntityManager entityManager;

	public void increaseReputation(Comment comment) {
		int currentVotes = comment.getVotes();
		comment.setVotes(currentVotes + 1);
		entityManager.refresh(comment);
	}

	public void decreaseReputation(Comment comment) {
		int currentVotes = comment.getVotes();
		comment.setVotes(currentVotes - 1);
		entityManager.refresh(comment);
	}

	public void increaseReputation(Post post) {
		int currentVotes = post.getVotes();
		post.setVotes(currentVotes + 1);
		entityManager.refresh(post);
	}

	public void decreaseReputation(Post post) {
		int currentVotes = post.getVotes();
		post.setVotes(currentVotes - 1);
		entityManager.refresh(post);
	}

	public Comment getTopCommentOnPost(Post post) {
		List<Comment> sortedList = sortCommentsByVotes(post);
		return sortedList.get(0);
	}

	private List<Comment> sortCommentsByVotes(Post post) {
		List<Comment> comments = post.getComments();
		Collections.sort(comments, new ReputationComparator());
		return comments;
	}
}
