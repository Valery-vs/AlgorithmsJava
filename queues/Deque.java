/* *****************************************************************************
 *  Name: Valeriy Smirnov
 *  Date: 05/10/2020
 *  Description: A double-ended queue or deque is a generalization
 *              of a stack and a queue that supports adding and removing items from either
 *              the front or the back of the data structure
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<T> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.first == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<T> node = new Node<T>(item);
        this.size++;
        if (this.isEmpty()) {
            this.first = node;
            this.last = node;
            return;
        }

        node.next = this.first;
        this.first.prev = node;
        this.first = node;
    }

    // add the item to the back
    public void addLast(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<T> node = new Node<T>(item);
        this.size++;
        if (this.isEmpty()) {
            this.first = node;
            this.last = node;
            return;
        }

        node.prev = this.last;
        this.last.next = node;
        this.last = node;
    }

    // remove and return the item from the front
    public T removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        this.size--;
        this.first = this.first.next;
        this.first.prev = null;

        return this.first.item;
    }

    // remove and return the item from the back
    public T removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        this.size--;
        this.last = this.last.prev;
        this.last.next = null;

        return this.last.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            T item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        System.out
                .printf("Created deque: isEmpty - %s, size - %d\n", deque.isEmpty(), deque.size());

        deque.addFirst(10);
        deque.addFirst(9);
        deque.addLast(11);
        deque.addLast(12);

        System.out.printf("Added items: isEmpty - %s, size - %d\n", deque.isEmpty(), deque.size());

        System.out.println("items:");
        for (Integer i : deque) {
            System.out.printf("%d ", i);
        }

        System.out.println();

        System.out.printf("Remove first: %d\n", deque.removeFirst());
        System.out.printf("Remove last: %d\n", deque.removeLast());

        System.out
                .printf("Removed items: isEmpty - %s, size - %d\n", deque.isEmpty(), deque.size());

        System.out.println("items:");
        for (Integer i : deque) {
            System.out.printf("%d ", i);
        }
    }

}
