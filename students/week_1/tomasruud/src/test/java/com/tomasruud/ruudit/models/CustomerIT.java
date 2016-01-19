package com.tomasruud.ruudit.models;

import com.tomasruud.ruudit.testCases.EntityManagerBaseCase;
import org.junit.Test;

import javax.persistence.TypedQuery;

import static org.junit.Assert.assertEquals;

/**
 * @author Tomas Ruud
 * @since 18.01.2016
 */
public class CustomerIT extends EntityManagerBaseCase {

    @Test
    public void namedQueryForNorwegiansReturnsNorwegians() throws Exception {

        TypedQuery<Customer> norwegian = getManager().createNamedQuery( "allNorwegian", Customer.class );
        assertEquals( 3, norwegian.getResultList().size() );
    }

    @Test
    public void namedQueryForOsloReturnsOsloDudes() throws Exception {

        TypedQuery<Customer> allFromOslo = getManager().createNamedQuery( "allFromOslo", Customer.class );
        assertEquals( 2, allFromOslo.getResultList().size() );
    }
}