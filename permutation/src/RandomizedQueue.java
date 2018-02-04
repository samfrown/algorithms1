import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;

    private int front = 0;
    private int size = 0;

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] order;
        private int index = 0;
        private int count;

        RandomizedQueueIterator(int size) {
            this.order = StdRandom.permutation(items.length);
            this.count = size;
        }

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Item next() {
            if (count <= 0 || index >= order.length) throw new NoSuchElementException();
            while (items[order[index]] == null) {
                ++index;
            }
            count--;
            return items[order[index++]];
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (front >= items.length) {
            resize(items.length * 2);
        }
        items[front++] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        if (items[index] == null) {
            resize(size * 2);
        }
        Item item = items[index];
        items[index] = null;
        size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int index = StdRandom.uniform(size);
        if (items[index] == null) {
            resize(size + 1);
        }
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(size);
    }

    private void resize(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];
        front = 0;
        for (Item item : items) {
            if (item != null) {
                newItems[front++] = item;
            }
            if (front == size) break;
        }
        items = newItems;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        rq.enqueue(1);
        int result = rq.dequeue();

        if (result != 1) {
            System.out.println("FAIL: 1 != 1");
        }

        if (!rq.isEmpty()) {
            System.out.println("FAIL: RQ should be empty: " + rq.size());
        }
        rq.enqueue(39);
        rq.enqueue(42);

        if (rq.size() != 2) {
            System.out.println("FAIL: RQ size should be 2: " + rq.size());
        }
    }

}
