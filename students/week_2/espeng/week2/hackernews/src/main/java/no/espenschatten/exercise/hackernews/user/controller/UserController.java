package no.espenschatten.exercise.hackernews.user.controller;

import no.espenschatten.exercise.hackernews.user.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserController {

	@PersistenceContext(name = "hackernews")
	private EntityManager entityManager;

	public void createUser(String userName, String firstName, String lastName, String email, String password) {
		User user = new User(userName, firstName, lastName, email, password);
		entityManager.persist(user);
	}

	public void deleteUser(User user) {
		entityManager.remove(user);
	}
}
