package no.espenschatten.exercise.hackernews.post.controller;

import no.espenschatten.exercise.hackernews.post.entity.Post;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PostController {

	@PersistenceContext(name = "hackernews")
	private EntityManager entityManager;

	public void createNewPost(int userId, String content) {
		Post post = new Post(userId, content);
		entityManager.persist(post);
	}
}
