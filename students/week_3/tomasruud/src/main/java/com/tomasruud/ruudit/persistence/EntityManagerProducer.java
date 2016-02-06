package com.tomasruud.ruudit.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

    @Produces
    @PersistenceContext( unitName = "ruudit" )
    private EntityManager manager;
}
