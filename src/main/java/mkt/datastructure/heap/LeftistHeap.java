package mkt.datastructure.heap;

import java.util.Comparator;

/**
 * @param <E>
 * @since 0.0.1
 * @author mkt
 */
public class LeftistHeap<E> implements Heap<E> {
    
    private static class Node<K> {
        final K key;
        Node<K> left;
        Node<K> right;
        int rank;
        Node(K key) throws NullPointerException {
            if (key == null)
                throw new NullPointerException();
            this.key = key;
        }
        @SuppressWarnings("unchecked")
        boolean lessThan(Node<K> n, Comparator<? super K> cmp) throws ClassCastException {
            assert n != null;
            if (cmp != null)
                return cmp.compare(key, n.key) < 0;
            return ((Comparable<? super K>) key).compareTo(n.key) < 0;
        }
    }
    
    private final Comparator<? super E> comparator;
    
    private Node<E> root;
    private int size;
    
    public LeftistHeap() {
        comparator = null;
    }
    
    public LeftistHeap(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }
    
    @Override
    public E findMin() {
        if (root == null)
            return null;
        return root.key;
    }
    
    @Override
    public E deleteMin() {
        if (root == null)
            return null;
        E e = root.key;
        root = mergeSubtrees(root.left, root.right);
        --size;
        return e;
    }
    
    @Override
    public void insert(E e) throws ClassCastException, NullPointerException {
        root = mergeSubtrees(new Node<>(e), root);
        size++;
    }
    
    @Override
    public void decreaseKey(E e0, E e1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void merge(Heap<E> h) throws ClassCastException, NullPointerException {
        if (h.isEmpty())
            return;
        @SuppressWarnings("unchecked")
        LeftistHeap<E> lh = (LeftistHeap<E>) h;
        root = mergeSubtrees(lh.root, root);
        size += lh.size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    private Node mergeSubtrees(Node<E> x, Node<E> y) throws ClassCastException {
        if (y == null)
            return x;
        assert x != null;
        if (x.lessThan(y, comparator)) {
            Node<E> tmp = x;
            x = y;
            y = tmp;
        }
        y.right = mergeSubtrees(x, y.right);
        assert y.right != null;
        if (y.left == null || y.left.rank < y.right.rank) {
            Node<E> tmp = y.left;
            y.left = y.right;
            y.right = tmp;
        }
        if (y.right != null)
            y.rank = y.right.rank + 1;
        return y;
    }
    
}
