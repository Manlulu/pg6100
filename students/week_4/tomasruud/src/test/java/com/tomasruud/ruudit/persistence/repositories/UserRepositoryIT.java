package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.cases.ContainerTestCase;
import com.tomasruud.ruudit.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserRepositoryIT extends ContainerTestCase {

    private UserRepository repository;

    @Before
    public void setRepository() throws Exception {

        repository = getManagedClass( UserRepository.class );
    }

    @Test
    public void getAllReturnsAll() throws Exception {

        int count = repository.getAll().size();

        assertTrue( count > 0 );
    }

    @Test
    public void getOneReturnsOne() throws Exception {

        Long id = repository.getAll().iterator().next().getId();

        User user = repository.get( id );

        assertNotNull( user );
        assertEquals( id, user.getId() );
    }

    @Test
    public void createCreatesNewEntity() throws Exception {

        User user = new User();
        user.setUsername( "some-valid-username" );
        user.setPassword( "some password" );

        int before = repository.getAll().size();

        repository.create( user );

        assertNotNull( user.getId() );
        assertEquals( before + 1, repository.getAll().size() );
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

        repository.create( user );

        assertNotNull( user.getPasswordHash() );
        assertNotNull( user.getSalt() );
        assertNull( user.getPassword() );
    }

    @Test
    public void updateUpdatesUser() throws Exception {

        User user = repository.getAll().iterator().next();

        String newName = user.getUsername() + "-somestuff";

        user.setUsername( newName );

        repository.update( user );

        assertEquals( newName, repository.get( user.getId() ).getUsername() );
    }


    @Test
    public void removeRemovesEntity() throws Exception {

        User user = repository.getAll().iterator().next();
        int count = repository.getAll().size();

        repository.destroy( user );

        assertEquals( count - 1, repository.getAll().size() );
    }
}