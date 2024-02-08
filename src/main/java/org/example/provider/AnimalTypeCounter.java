package org.example.provider;

import java.util.concurrent.atomic.LongAdder;

public final class AnimalTypeCounter {

    //    private static volatile long counter;
//    private static final AtomicLong counter;
    private static final LongAdder counter;

    static {
//        counter = 0L;
//        counter = new AtomicLong();
        counter = new LongAdder();
    }

    private AnimalTypeCounter() {
    }

    public static long getCount() {
//        return counter;

//        return counter.longValue();

        return counter.longValue();
    }

    public static void increment() {
//        synchronized (AnimalTypeCounter.class) {
//            counter++;
//        }

//        counter.increment();

        counter.increment();
    }

}
