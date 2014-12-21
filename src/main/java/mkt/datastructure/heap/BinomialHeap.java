package mkt.datastructure.heap;

import java.util.Comparator;

/**
 * @param <E>
 * @since 0.0.1
 * @author mkt
 */
public class BinomialHeap<E> implements Heap<E> {
    
    private class Node {
        E key;
        int order;
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
    
    private Node head;
    private int size;
    
    public BinomialHeap() {
        comparator = null;
    }
    
    public BinomialHeap(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }
    
    @Override
    public E findMin() {
        if (head == null)
            return null;
        Node min = head;
        Node cur = head.next;
        while (cur != null) {
            if (cur.lessThan(min))
                min = cur;
            cur = cur.next;
        }
        return min.key;
    }
    
    @Override
    public E deleteMin() throws ClassCastException {
        if (head == null)
            return null;
        Node minPrev = null;
        Node min = head;
        Node prev = head;
        Node cur = head.next;
        while (cur != null) {
            if (cur.lessThan(min)) {
                minPrev = prev;
                min = cur;
            }
            prev = cur;
            cur = cur.next;
        }
        if (minPrev == null) // i.e. if head is the minimum
            head = head.next;
        else
            minPrev.next = min.next;
        for (Node n = min.child, next; n != null; n = next) {
            next = n.next;
            n.next = null;
            insertSubtree(n);
        }
        --size;
        return min.key;
    }
    
    @Override
    public void insert(E e) throws ClassCastException, NullPointerException {
        insertSubtree(new Node(e));
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
        BinomialHeap<E> bh = (BinomialHeap<E>) h;
        for (Node n = bh.head, next; n != null; n = next) {
            next = n.next;
            n.next = null;
            insertSubtree(n);
        }
        size += bh.size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Inserts the tree represented by the given node into the heap.
     * node is expected to represent an independent tree.
     * @param node
     * @throws ClassCastException 
     */
    private void insertSubtree(Node node) throws ClassCastException {
        assert node != null;
        assert node.next == null;
        Node prev = null;
        Node cur = head;
        while (cur != null) {
            if (node.order < cur.order) {
                node.next = cur;
                break;
            }
            Node next = cur.next;
            if (node.order == cur.order) {
                if (prev != null)
                    prev.next = next;
                node = mergeSubtrees(node, cur);
            } else { // i.e. if node.order > curr.order
                prev = cur;
            }
            cur = next;
        }
        if (prev == null)
            head = node;
        else
            prev.next = node;
    }
    
    /**
     * Merges the trees represented by the given two nodes.
     * Both nodes should be of the same order.
     * x is expected to represent an independent tree.
     * y is expected to represent an existing tree in the heap.
     * The returned node represents an unified and independent tree.
     * @param x
     * @param y
     * @return
     * @throws ClassCastException 
     */
    private Node mergeSubtrees(Node x, Node y) throws ClassCastException {
        assert x != null && y != null;
        assert x.order == y.order;
        assert x.next == null;
        if (x.lessThan(y)) {
            x.order++;
            y.next = x.child;
            x.child = y;
            return x;
        }
        y.order++;
        x.next = y.child;
        y.child = x;
        y.next = null;
        return y;
    }
    
}