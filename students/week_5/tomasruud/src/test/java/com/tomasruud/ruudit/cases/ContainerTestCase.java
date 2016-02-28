package com.tomasruud.ruudit.cases;

import com.tomasruud.ruudit.generators.SeedGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import java.io.File;
import java.util.HashMap;

public abstract class ContainerTestCase {

    private static EJBContainer container;
    private static Context context;

    @BeforeClass
    public static void setupContainer() throws Exception {

        HashMap<String, Object> config = new HashMap<>();
        config.put( EJBContainer.MODULES, new File( "target/classes" ) );

        container = EJBContainer.createEJBContainer( config );
        context = container.getContext();
    }

    @Before
    public void generateSeeds() throws Exception {

        SeedGenerator.seed();
    }

    @After
    public void cleanDatabase() throws Exception {

        SeedGenerator.reset();
    }

    @AfterClass
    public static void closeContainer() throws Exception {

        if( context != null )
            context.close();

        if( container != null )
            container.close();
    }

    public static <T> T getManagedClass( Class<T> clazz ) {

        try {

            return ( T ) context.lookup( "java:global/classes/" + clazz.getSimpleName() +
                    "!" + clazz.getName() );

        } catch( NamingException e ) {

            e.printStackTrace();
        }

        return null;
    }
}