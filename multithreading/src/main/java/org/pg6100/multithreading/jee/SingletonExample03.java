package org.pg6100.multithreading.jee;

import org.pg6100.multithreading.Counter;

import javax.ejb.AccessTimeout;
import javax.ejb.Singleton;
import java.util.concurrent.TimeUnit;

@Singleton
public class SingletonExample03 implements Counter {

    private int x;

    @AccessTimeout(value = 2 , unit = TimeUnit.MILLISECONDS)
    @Override
    public void incrementCounter() {
        x = x + 1;
    }

    @Override
    public int getCounter() {
        return x;
    }
}
