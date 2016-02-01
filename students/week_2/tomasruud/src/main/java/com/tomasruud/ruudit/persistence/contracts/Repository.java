package com.tomasruud.ruudit.persistence.contracts;

import java.util.List;

public interface Repository<Key, Type> {

    List<Type> getAll();

    Type get( Key id );

    Type create( Type object );

    Type update( Type object );

    boolean destroy( Type object );
}
