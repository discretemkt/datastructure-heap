package mkt.datastructure.heap;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * @since 0.0.1
 * @author mkt
 */
public class BenchMarkTest {
    
    public static void main(String... args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Heap<Integer> ph = new PairingHeap<>();
        Heap<Integer> lh = new LeftistHeap<>();
        Heap<Integer> sh = new SkewHeap<>();
        int size = 1024 * 1024 - 1;
        warmUp(pq, size);
        warmUp(ph, size);
        warmUp(lh, size);
        warmUp(sh, size);
        System.out.printf("%s\n", pq.getClass().getName());
        execute(pq, size);
        System.out.printf("\n");
        System.out.printf("%s\n", lh.getClass().getName());
        execute(lh, size);
        System.out.printf("\n");
        System.out.printf("%s\n", ph.getClass().getName());
        execute(ph, size);
        System.out.printf("\n");
        System.out.printf("%s\n", sh.getClass().getName());
        execute(sh, size);
        System.out.printf("\n");
    }
    
    private static void warmUp(PriorityQueue<Integer> queue, int size) {
        int[] randomNumbers = generateRandomNumbers(size);
        doInsertionOnly(queue, randomNumbers);
        doInsertionAndDeletion(queue, randomNumbers);
        doDeletionOnly(queue);
        System.gc();
    }
    
    private static void warmUp(Heap<Integer> heap, int size) {
        int[] randomNumbers = generateRandomNumbers(size);
        doInsertionOnly(heap, randomNumbers);
        doInsertionAndDeletion(heap, randomNumbers);
        doDeletionOnly(heap);
        System.gc();
    }
    
    private static void execute(PriorityQueue<Integer> queue, int size) {
        int[] randomNumbers = generateRandomNumbers(size);
        long t;
        for (int i = 0; i < 10; i++) {
            t = doInsertionOnly(queue, randomNumbers);
            System.out.printf("%,8d ", t);
            t = doInsertionAndDeletion(queue, randomNumbers);
            System.out.printf("%,8d ", t);
            t = doDeletionOnly(queue);
            System.out.printf("%,8d\n", t);
        }
        System.gc();
    }
    
    private static void execute(Heap<Integer> heap, int size) {
        int[] randomNumbers = generateRandomNumbers(size);
        long t;
        for (int i = 0; i < 10; i++) {
            t = doInsertionOnly(heap, randomNumbers);
            System.out.printf("%,8d ", t);
            t = doInsertionAndDeletion(heap, randomNumbers);
            System.out.printf("%,8d ", t);
            t = doDeletionOnly(heap);
            System.out.printf("%,8d\n", t);
        }
        System.gc();
    }
    
    private static int[] generateRandomNumbers(int size) {
        Random random = new Random();
        int[] randomNumbers = new int[size];
        for (int i = 0; i < size; i++)
            randomNumbers[i] = random.nextInt(size);
        return randomNumbers;
    }
    
    private static long doInsertionOnly(PriorityQueue<Integer> queue, int[] randomNumbers) {
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < randomNumbers.length; i++)
            queue.add(randomNumbers[i]);
        return System.currentTimeMillis() - t0;
    }
    
    private static long doInsertionOnly(Heap<Integer> heap, int[] randomNumbers) {
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < randomNumbers.length; i++)
            heap.insert(randomNumbers[i]);
        return System.currentTimeMillis() - t0;
    }
    
    private static long doInsertionAndDeletion(PriorityQueue<Integer> queue, int[] randomNumbers) {
        Random random = new Random();
        int count = 0;
        long t0 = System.currentTimeMillis();
        while (count < randomNumbers.length) {
            if (random.nextBoolean()) {
                queue.add(randomNumbers[count]);
                count++;
                continue;
            }
            if (!queue.isEmpty())
                queue.remove();
        }
        return System.currentTimeMillis() - t0;
    }
    
    private static long doInsertionAndDeletion(Heap<Integer> heap, int[] randomNumbers) {
        Random random = new Random();
        int count = 0;
        long t0 = System.currentTimeMillis();
        while (count < randomNumbers.length) {
            if (random.nextBoolean()) {
                heap.insert(randomNumbers[count]);
                count++;
                continue;
            }
            if (!heap.isEmpty())
                heap.deleteMin();
        }
        return System.currentTimeMillis() - t0;
    }
    
    private static long doDeletionOnly(PriorityQueue<Integer> queue) {
        long t0 = System.currentTimeMillis();
        while (!queue.isEmpty())
            queue.remove();
        return System.currentTimeMillis() - t0;
    }
    
    private static long doDeletionOnly(Heap<Integer> heap) {
        long t0 = System.currentTimeMillis();
        while (!heap.isEmpty())
            heap.deleteMin();
        return System.currentTimeMillis() - t0;
    }
    
}
