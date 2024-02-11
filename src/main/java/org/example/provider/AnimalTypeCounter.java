package org.example.provider;

import java.util.concurrent.locks.ReentrantLock;

public final class AnimalTypeCounter {

//    private static volatile long counter;
//    private static final AtomicLong counter;
//    private static final LongAdder counter;

//    private static final LongAccumulator counter;
//    private static final AtomicReference<LongAdder> counter;
//    private static final Map<String, Long> counter;

    private static volatile long counter;
    private static final ReentrantLock lock;

    static {
//        counter = 0L;
//        counter = new AtomicLong();
//        counter = new LongAdder();

//        counter = new LongAccumulator(Long::sum, 0L);
//        counter = new AtomicReference<>(new LongAdder());
//        counter = new ConcurrentHashMap<>();

        counter = 0L;
        lock = new ReentrantLock(true);
    }

    private AnimalTypeCounter() {
    }

    public static long getCount() {
//        synchronized (AnimalTypeCounter.class) {
//            return counter;
//        }

//        return counter;

//        return counter.longValue();

//        return counter.longValue();

//        var counterVal = counter.get();
//        return counterVal.longValue();

//        return counter.getOrDefault(CreateAnimalService.NAME, 0L);

        lock.lock();
        try {
            return counter;
        } finally {
            lock.unlock();
        }

    }

    public static void increment() {
//        synchronized (AnimalTypeCounter.class) {
//            counter++;
//        }

//        counter.increment();

//        counter.increment();

//        var counterVal = counter.get();
//        counterVal.increment();

//        var preVal = counter.putIfAbsent(CreateAnimalService.NAME, 1L);
//        if (Objects.nonNull(preVal)) {
//            counter.put(CreateAnimalService.NAME, (preVal + 1L));
//        }
//        // alt variant
//        counter.putIfAbsent(CreateAnimalService.NAME, 1L);
//        counter.computeIfPresent(CreateAnimalService.NAME, (key, value) -> (value + 1L));

        lock.lock();
        try {
            // Surprise ! :D
            counter++;
        } finally {
            lock.unlock();
        }

    }

}
