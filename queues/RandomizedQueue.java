/* *****************************************************************************
 *  Name: Valeriy Smirnov
 *  Date: 05/10/2020
 *  Description:  A randomized queue is similar to a stack or queue, except that
 *               the item removed is chosen uniformly at random among items in
 *               the data structure.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (this.n == this.items.length) {
            resize(2 * this.items.length, -1);
        }

        this.items[this.n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = this.getRandomIndex();
        Item result = this.items[index];

        if (this.n > 1 && this.n == this.items.length / 4) {
            resize(this.items.length / 2, index);
        }
        else {
            this.items[index] = this.items[this.n - 1];
            this.items[this.n - 1] = null;
        }

        this.n--;
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        return this.items[this.getRandomIndex()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private void resize(int capacity, int exludeIndex) {
        if (exludeIndex > -1) {
            capacity--;
        }

        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < this.n; i++) {
            if (exludeIndex < 0 || i < exludeIndex) {
                copy[i] = this.items[i];
            }
            else if (i > exludeIndex) {
                copy[i - 1] = this.items[i];
            }
        }

        this.items = copy;
    }

    private int getRandomIndex() {
        return StdRandom.uniform(this.size());
    }

    private class QueueIterator implements Iterator<Item> {
        private int current = 0;
        private int[] itemsOrder;

        public QueueIterator() {
            this.itemsOrder = StdRandom.permutation(n);
        }

        public boolean hasNext() {
            return current < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            Item result = items[this.itemsOrder[this.current]];
            this.current++;
            return result;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue1 = new RandomizedQueue<Integer>();
        for (int i = 0; i < 12000; i++) {
            randomizedQueue1.enqueue(1);
            randomizedQueue1.enqueue(2);
            randomizedQueue1.enqueue(3);
            randomizedQueue1.enqueue(4);
            randomizedQueue1.enqueue(5);
            for (int q = 0; q < 5; q++) {
                Integer deq = randomizedQueue1.dequeue();
                if (deq == null) {
                    System.out.println("null");
                }

                for (Integer j : randomizedQueue1) {
                    if (j == null) {
                        System.out.println("null");
                    }
                }
            }
        }

        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        System.out
                .printf("Created deque: isEmpty - %s, size - %d\n", randomizedQueue.isEmpty(),
                        randomizedQueue.size());

        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(5);

        System.out.printf("Added items: isEmpty - %s, size - %d\n", randomizedQueue.isEmpty(),
                          randomizedQueue.size());

        System.out.println("items:");
        for (Integer i : randomizedQueue) {
            System.out.printf("%d ", i);
        }

        System.out.println();

        System.out.printf("Sample: %d\n", randomizedQueue.sample());
        System.out.printf("Remove: %d\n", randomizedQueue.dequeue());

        System.out
                .printf("Remove item: isEmpty - %s, size - %d\n", randomizedQueue.isEmpty(),
                        randomizedQueue.size());

        System.out.println("items:");
        for (Integer i : randomizedQueue) {
            System.out.printf("%d ", i);
        }
    }

}
