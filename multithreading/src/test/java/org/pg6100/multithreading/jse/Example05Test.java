package org.pg6100.multithreading.jse;

import org.pg6100.multithreading.Counter;
import org.pg6100.multithreading.CounterTestBase;

import static org.junit.Assert.*;

public class Example05Test extends CounterTestBase {

    @Override
    protected Counter getCounter() {
        return new Example05();
    }
}