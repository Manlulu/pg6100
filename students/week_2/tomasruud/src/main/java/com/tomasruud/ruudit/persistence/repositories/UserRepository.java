package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.models.User;

import javax.ejb.Stateless;

@Stateless
public class UserRepository extends AbstractEntityManagerRepository<Long, User> {

    public UserRepository() {

        super( User.class );
    }
}
