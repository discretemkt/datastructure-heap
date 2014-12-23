package mkt.datastructure.heap;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @since 0.0.1
 * @author mkt
 */
public class ReconciliationTest {
    
    @Test
    public void testBinaryHeap() {
        Heap<Integer> heap0 = new BinaryHeap<>();
        Heap<Integer> heap1 = new BinaryHeap<>();
        testWithRandomNumbers(heap0, heap1, 1024 * 1024 - 1);
    }
    
    @Test
    public void testBinomialHeap() {
        Heap<Integer> heap0 = new BinomialHeap<>();
        Heap<Integer> heap1 = new BinomialHeap<>();
        testWithRandomNumbers(heap0, heap1, 1024 * 1024 - 1);
    }
    
    @Test
    public void testPairingHeap() {
        Heap<Integer> heap0 = new PairingHeap<>();
        Heap<Integer> heap1 = new PairingHeap<>();
        testWithRandomNumbers(heap0, heap1, 1024 * 1024 - 1);
    }
    
    @Test
    public void testSkewHeap() {
        Heap<Integer> heap0 = new SkewHeap<>();
        Heap<Integer> heap1 = new SkewHeap<>();
        testWithRandomNumbers(heap0, heap1, 1024 * 1024 - 1);
    }
    
    private void testWithRandomNumbers(Heap<Integer> heap0, Heap<Integer> heap1, int size) {
        Random random = new Random();
        int[] randomNumbers0 = new int[size];
        int[] randomNumbers1 = new int[size];
        for (int i = 0; i < size; i++) {
            randomNumbers0[i] = random.nextInt(size);
            randomNumbers1[i] = random.nextInt(size);
        }
        Queue<Integer> queue0 = new PriorityQueue<>();
        for (int randomNumber : randomNumbers0) {
            heap0.insert(randomNumber);
            queue0.add(randomNumber);
        }
        Queue<Integer> queue1 = new PriorityQueue<>();
        for (int randomNumber : randomNumbers1) {
            heap1.insert(randomNumber);
            queue1.add(randomNumber);
        }
        heap0.merge(heap1);
        queue0.addAll(queue1);
        while (!queue0.isEmpty() || !heap0.isEmpty())
            assertThat(heap0.deleteMin(), is(queue0.remove()));
    }
    
}