package org.pg6100.jta.ejb;

import org.pg6100.jta.data.Foo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class QueriesEJB {

    @PersistenceContext
    private EntityManager em;

    public int deleteAll(){
        return em.createNamedQuery(Foo.DELETE_ALL).executeUpdate();
    }

    public List<Foo> findAll(){
        return em.createNamedQuery(Foo.FIND_ALL).getResultList();
    }

    public boolean isInDB(String name){
        return em.find(Foo.class, name) != null;
    }
}
