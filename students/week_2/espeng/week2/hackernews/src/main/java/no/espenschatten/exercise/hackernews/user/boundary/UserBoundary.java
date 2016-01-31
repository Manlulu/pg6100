package no.espenschatten.exercise.hackernews.user.boundary;

import no.espenschatten.exercise.hackernews.user.controller.UserController;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class UserBoundary {

	@Inject
	UserController userController;

	public void createUser(String userName, String firstName, String lastName, String email, String password) {
		userController.createUser(userName, firstName, lastName, email, password);
	}
}
