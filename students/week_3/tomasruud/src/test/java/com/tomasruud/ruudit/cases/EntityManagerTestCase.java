package com.tomasruud.ruudit.cases;

import com.tomasruud.ruudit.persistence.contracts.EntityManagerManaged;
import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class EntityManagerTestCase<T extends EntityManagerManaged> {

    private static final String UNIT_NAME = "ruudit";

    private EntityManagerFactory factory;
    private EntityManager manager;

    private Class<T> clazz;

    private T persistence;

    public EntityManagerTestCase( Class<T> clazz ) {

        this.clazz = clazz;
    }

    public EntityManager getManager() {

        return manager;
    }

    @Before
    public void openFactory() throws Exception {

        factory = Persistence.createEntityManagerFactory( UNIT_NAME );
    }

    @After
    public void closeFactory() {

        factory.close();
    }

    public T getPersistence() {

        return persistence;
    }

    @Before
    public void startTransactionAndSetManager() throws Exception {

        manager = factory.createEntityManager();
        manager.getTransaction().begin();

        persistence = clazz.newInstance();
        persistence.setManager( manager );
    }

    @After
    public void commitTransactionAndCloseManager() throws Exception {

        if( manager.getTransaction().isActive() )
            manager.getTransaction().commit();

        if( manager.isOpen() )
            manager.close();
    }
}
