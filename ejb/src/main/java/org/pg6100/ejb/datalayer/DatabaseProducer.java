package org.pg6100.ejb.datalayer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class DatabaseProducer {

    @Produces
    @PersistenceContext(unitName = "ejbExample")
    private EntityManager em;
}
