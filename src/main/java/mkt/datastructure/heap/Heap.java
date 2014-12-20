package mkt.datastructure.heap;

/**
 * Represents a min heap.
 * 
 * @param <E>
 * @since 0.0.1
 * @author mkt
 */
public interface Heap<E> {
    
    public E findMin();
    
    public E deleteMin();
    
    public void insert(E e);
    
    public void decreaseKey(E e0, E e1);
    
    public void merge(Heap<E> h);
    
    public boolean isEmpty();
    
}