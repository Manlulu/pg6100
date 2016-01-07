package org.pg6100.injection;

import org.junit.Test;

public abstract class BaseSuite {

    /**
     * Method used by all subclasses test suites to specify which injector they are testing
     */
    protected abstract Injector getInjector();



    @Test (expected = IllegalArgumentException.class)
    public void testNullClass(){
        Injector injector = getInjector();
        injector.createInstance(null);
    }


}
