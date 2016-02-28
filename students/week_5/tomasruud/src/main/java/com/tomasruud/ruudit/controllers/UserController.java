package com.tomasruud.ruudit.controllers;

import com.tomasruud.ruudit.helpers.Flash;
import com.tomasruud.ruudit.models.User;
import com.tomasruud.ruudit.persistence.repositories.UserRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class UserController {

    @Inject
    private UserRepository repository;

    private User user;
    private String passwordConfirmation;

    @PostConstruct
    public void setup() {

        user = new User();
    }

    public String create() {

        if( user.getPassword() == null || user.getPassword().isEmpty() ) {

            Flash.showError( "You must specify a password" );
            return "";
        }

        if( passwordConfirmation == null || passwordConfirmation.isEmpty() ) {

            Flash.showError( "You must confirm your password" );
            return "";
        }

        if( !passwordConfirmation.equals( user.getPassword() ) ) {

            Flash.showError( "Passwords doesn't match" );
            return "";
        }

        user = repository.create( user );

        if( user == null )
            return "";

        Flash.showSuccess( "User created" );

        return "/sessions/new.xhtml";
    }

    public User getUser() {

        return user;
    }

    public void setUser( User user ) {

        this.user = user;
    }

    public String getPasswordConfirmation() {

        return passwordConfirmation;
    }

    public void setPasswordConfirmation( String passwordConfirmation ) {

        this.passwordConfirmation = passwordConfirmation;
    }
}
