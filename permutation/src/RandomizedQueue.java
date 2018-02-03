import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    Item[] items;

    private class RandomizedQueueIterator<Item> implements Iterator {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Item next() {
            return null;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return items.length;
    }

    // add the item
    public void enqueue(Item item) {
    }

    // remove and return a random item
    public Item dequeue() {
        return items[0];
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return items[0];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }
}
