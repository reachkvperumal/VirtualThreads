package com.kv.jdk21;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class VirtualThread {

    private static final int COUNTER_SIZE = 10_000;

    private static final String OS_THREAD = "OS THREADS";

    private static final String VIRTUAL_THREAD = "VIRTUAL THREADS";

    private static Instant getTime() {
        return Instant.now();
    }

    private static Runnable run(int a,String type) {
        AtomicInteger atomicInteger = new AtomicInteger();
       // System.out.printf("Working on method %s - Iterator count %d%n",type,a);
        return () -> {
            try {
                atomicInteger.incrementAndGet();
                Thread.sleep(Duration.ofSeconds(1));
                atomicInteger.incrementAndGet();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        };
    }

    private static void osThread() {
        System.out.printf("Started %s...%n",OS_THREAD);
        Instant start = getTime();
        try (ExecutorService service = Executors.newFixedThreadPool(100)) {
            IntStream.range(0, COUNTER_SIZE).forEach(action -> service.submit(run(action,OS_THREAD)));
        }
        long seconds = Duration.between(start, getTime()).getSeconds();
        System.out.printf("Total Elapsed Time for %s in seconds: %d%n", OS_THREAD, seconds);
    }

    private static void virtualThread() {
        System.out.printf("Started %s...%n",VIRTUAL_THREAD);
        Instant start = getTime();
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, COUNTER_SIZE).forEach(action -> service.submit(run(action,VIRTUAL_THREAD)));
        }
        long seconds = Duration.between(start, getTime()).getSeconds();
        System.out.printf("Total Elapsed Time for %s in Seconds: %d%n", VIRTUAL_THREAD, seconds);
    }

    public static void main(String[] args) {
        osThread();
        virtualThread();
    }
}
