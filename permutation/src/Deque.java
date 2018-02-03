import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node backward;
        Node forward;

        Node(Item item, Node forward, Node backward) {
            this.item = item;
            this.forward = forward;
            this.backward = backward;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        DequeIterator(Node current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item currentItem = current.item;
            current = current.backward;
            return currentItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node front;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
        tail = front = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return front == null && tail == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldfront = front;
        front = new Node(item, null, oldfront);
        if (tail == null) {
            tail = front;
        }
        ++size;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldtail = tail;
        tail = new Node(item, oldtail, null);
        if (front == null) {
            front = tail;
        }
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (front == null) throw new NoSuchElementException();

        Item item = front.item;
        front = front.backward;
        if (front != null) {
            front.forward = null;
        }
        remove();
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (tail == null) throw new NoSuchElementException();
        Item item = tail.item;
        tail = tail.forward;
        if (tail != null) {
            tail.backward = null;
        }
        remove();
        return item;
    }

    private void remove() {
        --size;
        if (tail == null) {
            front = null;
        } else if (front == null) {
            tail = null;
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(front);
    }

    // unit testing (optional)
    public static void main(String[] args) {
    }
}