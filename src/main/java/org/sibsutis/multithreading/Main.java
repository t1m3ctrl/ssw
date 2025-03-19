package org.sibsutis.multithreading;

import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final int ARRAY_SIZE = 100_000_000;
    private static final int NUM_THREADS = 5;

    public static void main(String[] args) {
        int[] array = new Random().ints(ARRAY_SIZE, 1, 10).toArray();
        Thread[] threads = new Thread[NUM_THREADS];
        SumTask[] tasks = new SumTask[NUM_THREADS];

        long startTime = System.currentTimeMillis();
        long[] partialSums = new long[NUM_THREADS];

        int chunkSize = ARRAY_SIZE / NUM_THREADS;
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * chunkSize;
            int end = (i == NUM_THREADS - 1) ? ARRAY_SIZE : start + chunkSize;
            tasks[i] = new SumTask(array, start, end, i, partialSums);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        long totalSum = 0;
        for (long sum : partialSums) {
            totalSum += sum;
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Total sum (Stream API): " + Arrays.stream(array).asLongStream().sum());
        long singleThreadSum = 0;
        for (long num : array) {
            singleThreadSum += num;
        }
        System.out.println("Total sum (single-threaded): " + singleThreadSum);
        System.out.println("Total sum (multi-threaded): " + totalSum);
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
}

class SumTask implements Runnable {
    private final int[] array;
    private final int start, end, threadId;
    private final long[] partialSums;

    SumTask(int[] array, int start, int end, int threadId, long[] partialSums) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.threadId = threadId;
        this.partialSums = partialSums;
    }

    @Override
    public void run() {
        long sum = 0;
        long threadStartTime = System.currentTimeMillis();

        for (int i = start; i < end; i++) {
            sum += array[i];

            // Имитация задержки
            if ((i - start) % (( Math.random() * (25000-5000) ) + 25000) == 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if ((i - start) % (Math.max(1, (end - start) / 100)) == 0) {
                printProgress(i - start, end - start);
            }
        }

        partialSums[threadId] = sum;
        long threadEndTime = System.currentTimeMillis();
        System.out.printf("Thread %d finished in %d ms\n", threadId, (threadEndTime - threadStartTime));
    }

    private void printProgress(int done, int total) {
        int percent = (int) ((done / (double) total) * 100);
        int progressBars = percent / 5;
        String progress = "[" + "=".repeat(Math.max(0, progressBars)) +
                String.format("%d%%=>", percent) +
                "#".repeat(Math.max(0, 20 - progressBars)) +
                "]";
        System.out.printf("Thread %d: %s\n", threadId, progress);
    }

}
