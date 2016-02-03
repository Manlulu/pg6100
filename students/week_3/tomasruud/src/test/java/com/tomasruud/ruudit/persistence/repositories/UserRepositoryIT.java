package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.cases.EntityManagerTestCase;
import com.tomasruud.ruudit.models.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserRepositoryIT extends EntityManagerTestCase<UserRepository> {

    public UserRepositoryIT() {

        super( UserRepository.class );
    }

    @Test
    public void getAllReturnsAll() throws Exception {

        int count = getPersistence().getAll().size();

        assertTrue( count > 0 );
    }

    @Test
    public void getOneReturnsOne() throws Exception {

        Long id = getPersistence().getAll().iterator().next().getId();

        User user = getPersistence().get( id );

        assertNotNull( user );
        assertEquals( id, user.getId() );
    }

    @Test
    public void createCreatesNewEntity() throws Exception {

        User user = new User();
        user.setUsername( "some-valid-username" );
        user.setPassword( "some password" );

        int before = getPersistence().getAll().size();

        getPersistence().create( user );

        assertNotNull( user.getId() );
        assertEquals( before + 1, getPersistence().getAll().size() );
    }

    @Test
    public void creationOfUserCreatesPasswordHashAndSalt() throws Exception {

        User user = new User();
        user.setUsername( "Something" );

        String password = "something";
        user.setPassword( password );

        assertNull( user.getPasswordHash() );
        assertNull( user.getSalt() );
        assertNotNull( user.getPassword() );

        getPersistence().create( user );

        assertNotNull( user.getPasswordHash() );
        assertNotNull( user.getSalt() );
        assertNull( user.getPassword() );
    }

    @Test
    public void updateUpdatesUser() throws Exception {

        User user = getPersistence().getAll().iterator().next();

        String newName = user.getUsername() + "-somestuff";

        user.setUsername( newName );

        getPersistence().update( user );

        assertEquals( newName, getPersistence().get( user.getId() ).getUsername() );
    }


    @Test
    public void removeRemovesEntity() throws Exception {

        User user = getPersistence().getAll().iterator().next();
        int count = getPersistence().getAll().size();

        getPersistence().destroy( user );

        assertEquals( count - 1, getPersistence().getAll().size() );
    }
}