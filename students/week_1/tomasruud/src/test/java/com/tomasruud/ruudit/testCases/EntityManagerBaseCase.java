package com.tomasruud.ruudit.testCases;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Tomas Ruud
 * @since 18.01.2016
 */
public abstract class EntityManagerBaseCase {

    private EntityManager manager;
    private static EntityManagerFactory factory;

    private static final String UNIT_NAME = "ruudit";

    @BeforeClass
    public static void createFactory() throws Exception {

        factory = Persistence.createEntityManagerFactory( UNIT_NAME );
    }

    @AfterClass
    public static void closeFactory() throws Exception {

        factory.close();
    }

    @Before
    public void setEntityManager() throws Exception {

        manager = factory.createEntityManager();
    }

    public EntityManager getManager() {

        return manager;
    }

    public EntityManagerFactory getFactory() {

        return factory;
    }
}
