package org.pg6100.jta.ejb;

import org.pg6100.jta.data.Foo;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EJB_02_abort {

    @PersistenceContext
    private EntityManager em;

    public void createTwoCopies(String name){

        try {
            Foo foo = new Foo(name);
            Foo copy = new Foo(name);

            em.persist(foo);
            em.persist(copy);//should fail, because same id
        }catch (Exception e){
            //even if catch exception here, the container will propagate it
        }
    }
}
