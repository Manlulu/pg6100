package org.pg6100.multithreading.jse;

import org.pg6100.multithreading.Counter;

public class Example04  implements Counter {

    /**
     * No  "volatile"
     */
    private int x;

    @Override
    public void incrementCounter() {
        synchronized (this) {
            x = x + 1;
        }
    }

    @Override
    public int getCounter() {
        return x;
    }
}