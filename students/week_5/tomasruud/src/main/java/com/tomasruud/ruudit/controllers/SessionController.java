package com.tomasruud.ruudit.controllers;

import com.tomasruud.ruudit.helpers.Flash;
import com.tomasruud.ruudit.models.User;
import com.tomasruud.ruudit.persistence.services.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class SessionController implements Serializable {

    private User user;

    private User currentUser;

    @Inject
    private UserService service;

    @PostConstruct
    public void setup() {

        user = new User();
    }

    public String create() {

        if( getUser().getUsername() == null || getUser().getUsername().isEmpty() ) {

            Flash.showError( "You must provide a username" );
            return "";
        }

        if( getUser().getPassword() == null || getUser().getPassword().isEmpty() ) {

            Flash.showError( "You must provide a password" );
            return "";
        }

        User match = service.findByUsername( getUser().getUsername() );

        if( match == null ) {

            Flash.showError( "That user does not exist" );
            return "";
        }

        if( !match.comparePassword( getUser().getPassword() ) ) {

            Flash.showError( "Incorrect password" );
            return "";
        }

        Flash.showSuccess( "Successfully logged in" );
        setCurrentUser( match );

        return "/index.xhtml";
    }

    public String destroy() {

        setCurrentUser( null );
        Flash.showSuccess( "Successfully logged out" );

        return "/sessions/new.xhtml";
    }

    public User getUser() {

        return user;
    }

    public void setUser( User user ) {

        this.user = user;
    }

    public User getCurrentUser() {

        return currentUser;
    }

    public void setCurrentUser( User currentUser ) {

        this.currentUser = currentUser;
    }

    public boolean isLoggedIn() {

        return currentUser != null;
    }
}
