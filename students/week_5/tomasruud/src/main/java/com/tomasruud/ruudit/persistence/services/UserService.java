package com.tomasruud.ruudit.persistence.services;

import com.tomasruud.ruudit.models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

@Stateless
public class UserService implements Serializable {

    @Inject
    private EntityManager manager;

    public User findByUsername( String username ) {

        if( username == null )
            return null;

        try {

            return manager.createNamedQuery( User.QUERY_BY_NAME, User.class ).
                    setParameter( 1, username ).
                    getSingleResult();

        } catch( Exception e ) {

            return null;
        }
    }
}
