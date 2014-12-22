package mkt.datastructure.heap;

import java.util.Comparator;

/**
 * @param <E>
 * @since 0.0.1
 * @author mkt
 */
public class PairingHeap<E> implements Heap<E> {
    
    private class Node {
        E key;
        Node child;
        Node next;
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
    
    public PairingHeap() {
        comparator = null;
    }
    
    public PairingHeap(Comparator<? super E> comparator) {
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
        root = mergeManyNodes(root.child);
        --size;
        return e;
    }
    
    @Override
    public void insert(E e) throws ClassCastException, NullPointerException {
        Node n = new Node(e);
        if (root == null) {
            root = n;
        } else {
            if (n.lessThan(root)) {
                n.child = root;
                root = n;
            } else {
                n.next = root.child;
                root.child = n;
            }
        }
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
        PairingHeap<E> ph = (PairingHeap<E>) h;
        root = mergeTwoNodes(ph.root, root);
        size += ph.size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Merges nodes such that pairing is performed front-to-back and combining
     * is performed back-to-front.
     * @param n
     * @return
     * @throws ClassCastException 
     */
    private Node _mergeManyNodes(Node n) throws ClassCastException {
        if (n != null) {
            Node n0 = n.next;
            if (n0 != null) {
                Node n1 = n0.next;
                n = mergeTwoNodes(mergeTwoNodes(n, n0), _mergeManyNodes(n1));
            }
            n.next = null;
        }
        return n;
    }
    
    /**
     * Merges nodes such that both pairing and combining are performed
     * front-to-back at the same time.
     * @param n
     * @return
     * @throws ClassCastException 
     */
    private Node mergeManyNodes(Node n) throws ClassCastException {
        if (n == null)
            return null;
        Node nx1 = n.next;
        while (nx1 != null) {
            Node nx2 = nx1.next;
            Node nx3 = nx2 == null ? null : nx2.next;
            n = mergeTwoNodes(n, mergeTwoNodes(nx1, nx2));
            nx1 = nx3;
        }
        return n;
    }
    
    private Node mergeTwoNodes(Node n0, Node n1) throws ClassCastException {
        assert n0 != null;
        if (n1 == null)
            return n0;
        if (n0.lessThan(n1)) {
            n1.next = n0.child;
            n0.child = n1;
            n0.next = null;
            return n0;
        }
        n0.next = n1.child;
        n1.child = n0;
        n1.next = null;
        return n1;
    }
    
}