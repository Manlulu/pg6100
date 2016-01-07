package org.pg6100.injection.examples;

import org.pg6100.injection.BaseSuite;
import org.pg6100.injection.Injector;


public class InjectorImp01Test extends BaseSuite{

    @Override
    protected Injector getInjector() {
        return new InjectorImp01();
    }
}