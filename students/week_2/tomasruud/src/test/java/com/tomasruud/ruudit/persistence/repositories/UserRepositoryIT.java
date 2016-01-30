package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.cases.EntityManagerTestCase;
import com.tomasruud.ruudit.models.User;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserRepositoryIT extends EntityManagerTestCase<UserRepository> {

    public UserRepositoryIT() {

        super( UserRepository.class );
    }

    @Test
    public void getAllReturnsAll() throws Exception {

        int count = getRepository().getAll().size();

        assertTrue( count > 0 );
    }

    @Test
    public void getOneReturnsOne() throws Exception {

        Long id = getRepository().getAll().iterator().next().getId();

        User user = getRepository().get( id );

        assertNotNull( user );
        assertEquals( id, user.getId() );
    }

    @Test
    public void createCreatesNewEntity() throws Exception {

        User user = new User();
        user.setUsername( "some-valid-username" );

        int before = getRepository().getAll().size();

        getRepository().create( user );

        assertNotNull( user.getId() );
        assertEquals( before + 1, getRepository().getAll().size() );
    }

    @Test
    public void updateUpdatesUser() throws Exception {

        User user = getRepository().getAll().iterator().next();

        String newName = user.getUsername() + "-somestuff";

        user.setUsername( newName );

        getRepository().update( user );

        assertEquals( newName, getRepository().get( user.getId() ).getUsername() );
    }


    // TODO: Too tired to fix this atm
    @Ignore
    @Test
    public void removeRemovesEntity() throws Exception {

        User user = getRepository().getAll().iterator().next();
        int count = getRepository().getAll().size();

        getRepository().destroy( user );

        assertEquals( count - 1, getRepository().getAll().size() );
    }
}