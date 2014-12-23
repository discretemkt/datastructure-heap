package mkt.datastructure.heap;

import java.util.Comparator;

/**
 * @param <E>
 * @since 0.0.1
 * @author mkt
 */
public class BinaryHeap<E> implements Heap<E> {
    
    private class Node {
        E key;
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
    
    public BinaryHeap() {
        this.comparator = null;
    }
    
    public BinaryHeap(Comparator<? super E> comparator) {
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
        if (size == 1) {
            root = null;
        } else {
            Node n = findLast(root, size);
            n.left = root.left;
            n.right = root.right;
            heapify(n);
            root = n;
        }
        --size;
        return e;
    }
    
    @Override
    public void insert(E e) throws ClassCastException, NullPointerException {
        Node n = new Node(e);
        size++;
        root = insertNode(n, root, size);
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
        BinaryHeap<E> bh = (BinaryHeap<E>) h;
        while (!bh.isEmpty())
            insert(bh.deleteMin());
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    private Node findLast(Node p, int nav) {
        assert p != null;
        assert 1 < nav;
        if (nav == 2) {
            Node n = p.left;
            p.left = null;
            return n;
        }
        if (nav == 3) {
            Node n = p.right;
            p.right = null;
            return n;
        }
        int msb = Integer.highestOneBit(nav);
        if ((msb >> 1 & nav) == 0)
            return findLast(p.left, msb >> 1 | nav - msb);
        else
            return findLast(p.right, msb >> 1 | nav - msb);
    }
    
    private void heapify(Node n) throws ClassCastException {
        if (n.right != null && n.right.lessThan(n.left) && !n.lessThan(n.right)) {
            E tmp = n.key;
            n.key = n.right.key;
            n.right.key = tmp;
            heapify(n.right);
            return;
        }
        if (n.left != null && !n.lessThan(n.left)) {
            E tmp = n.key;
            n.key = n.left.key;
            n.left.key = tmp;
            heapify(n.left);
        }
    }
    
    private Node insertNode(Node n, Node p, int nav) throws ClassCastException {
        assert n != null;
        if (p == null)
            return n;
        assert 1 < nav;
        int msb = Integer.highestOneBit(nav);
        if ((msb >> 1 & nav) == 0) { // if n should be compared with p.left
            if (3 < nav)
                n = insertNode(n, p.left, msb >> 1 | nav - msb);
            if (n == p.left)
                return p;
            if (n.lessThan(p)) {
                p.left = n.left;
                n.left = p;
                Node tmp = p.right;
                p.right = n.right;
                n.right = tmp;
                return n;
            }
            p.left = n;
            return p;
        } else { // if n should be compared with p.right
            if (3 < nav)
                n = insertNode(n, p.right, msb >> 1 | nav - msb);
            if (n == p.right)
                return p;
            if (n.lessThan(p)) {
                p.right = n.right;
                n.right = p;
                Node tmp = p.left;
                p.left = n.left;
                n.left = tmp;
                return n;
            }
            p.right = n;
            return p;
        }
    }
    
}