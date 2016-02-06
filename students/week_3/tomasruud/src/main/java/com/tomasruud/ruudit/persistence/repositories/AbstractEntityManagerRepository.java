package com.tomasruud.ruudit.persistence.repositories;

import com.tomasruud.ruudit.persistence.contracts.EntityManagerManaged;
import com.tomasruud.ruudit.persistence.contracts.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractEntityManagerRepository<Key, Type>
        implements Repository<Key, Type>, EntityManagerManaged {

    @Inject
    private EntityManager manager;

    private Class<Type> clazz;

    protected AbstractEntityManagerRepository( Class<Type> clazz ) {

        this.clazz = clazz;
    }

    @Override
    public List<Type> getAll() {

        try {

            TypedQuery<Type> query = manager.createQuery(
                    "SELECT o FROM " + clazz.getSimpleName() + " o", clazz );

            return query.getResultList();

        } catch( Exception e ) { return null; }
    }

    @Override
    public Type get( Key id ) {

        try { return manager.find( clazz, id ); } catch( Exception e ) { return null; }
    }

    @Override
    public Type create( Type object ) {

        try {

            manager.persist( object );
            return object;

        } catch( Exception e ) { return null; }
    }

    @Override
    public Type update( Type object ) {

        try {

            if( !manager.contains( object ) )
                return manager.merge( object );

            return object;

        } catch( Exception e ) { return null; }
    }

    @Override
    public boolean destroy( Type object ) {

        try {

            manager.remove( object );

            object = null;

            return true;

        } catch( Exception e ) { return false; }
    }

    public void setManager( EntityManager manager ) {

        this.manager = manager;
    }

    protected EntityManager getManager() {

        return manager;
    }
}
