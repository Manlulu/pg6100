package com.tomasruud.ruudit.cases;

import com.tomasruud.ruudit.persistence.repositories.AbstractEntityManagerRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class EntityManagerTestCase<T extends AbstractEntityManagerRepository> {

    private static final String UNIT_NAME = "ruudit";

    private static EntityManagerFactory factory;
    private EntityManager manager;

    private Class<T> clazz;

    private T repository;

    public EntityManagerTestCase( Class<T> clazz ) {

        this.clazz = clazz;
    }

    @BeforeClass
    public static void openFactory() throws Exception {

        factory = Persistence.createEntityManagerFactory( UNIT_NAME );
    }

    @AfterClass
    public static void closeFactory() {

        factory.close();
    }

    public EntityManager getManager() {

        return manager;
    }

    public T getRepository() {

        return repository;
    }

    @Before
    public void startTransactionAndSetManager() throws Exception {

        manager = factory.createEntityManager();
        manager.getTransaction().begin();

        repository = clazz.newInstance();
        repository.setManager( manager );
    }

    @After
    public void commitTransactionAndCloseManager() throws Exception {

        if( manager.getTransaction().isActive() )
            manager.getTransaction().commit();

        if( manager.isOpen() )
            manager.close();
    }
}
