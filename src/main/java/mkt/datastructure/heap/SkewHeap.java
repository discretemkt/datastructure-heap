package mkt.datastructure.heap;

import java.util.Comparator;

/**
 * @param <E>
 * @since 0.0.1
 * @author mkt
 */
public class SkewHeap<E> implements Heap<E> {
    
    private class Node {
        final E key;
        Node left;
        Node right;
        Node(E key) throws NullPointerException {
            if (key == null)
                throw new NullPointerException();
            this.key = key;
        }
        @SuppressWarnings("unchecked")
        boolean lessThan(Node node) throws ClassCastException {
            assert node != null;
            if (comparator != null)
                return comparator.compare(key, node.key) < 0;
            return ((Comparable<? super E>) key).compareTo(node.key) < 0;
        }
    }
    
    private final Comparator<? super E> comparator;
    
    private Node root;
    private int size;
    
    public SkewHeap() {
        comparator = null;
    }
    
    public SkewHeap(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }
    
    @Override
    public E findMin() {
        if (root == null)
            return null;
        return root.key;
    }
    
    @Override
    public E deleteMin() throws ClassCastException {
        if (root == null)
            return null;
        E e = root.key;
        root = mergeSubtrees(root.left, root.right);
        --size;
        return e;
    }
    
    @Override
    public void insert(E e) throws ClassCastException, NullPointerException {
        root = mergeSubtrees(new Node(e), root);
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
        SkewHeap<E> sh = (SkewHeap<E>) h;
        root = mergeSubtrees(sh.root, root);
        size += sh.size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    private Node mergeSubtrees(Node x, Node y) throws ClassCastException {
        if (y == null)
            return x;
        assert x != null;
        if (x.lessThan(y)) {
            Node tmp = y;
            y = x;
            x = tmp;
        }
        y.right = mergeSubtrees(x, y.right);
        Node tmp = y.right;
        y.right = y.left;
        y.left = tmp;
        return y;
    }
    
}