package org.pg6100.injection.examples;

import org.pg6100.injection.Injector;

public class InjectorImp02 implements Injector {

    public <T> T createInstance(Class<T> klass) throws IllegalArgumentException {

        if(klass == null){
            throw new IllegalArgumentException("Null input");
        }

        return null;
    }
}
