package org.pg6100.multithreading.jse;

import org.pg6100.multithreading.Counter;

public class Example05 implements Counter {

    private volatile int x;

    @Override
    public synchronized void incrementCounter() {
        x = x + 1;
    }

    @Override
    public int getCounter() {
        return x;
    }
}