import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node tail;
    private int size;

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

    // construct an empty deque
    public Deque() {
        front = null;
        tail = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return front == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newnode = new Node(item, null, front);
        if (front != null) {
            front.forward = newnode;
            front = newnode;
        } else {
            front = newnode;
            tail = front;
        }
        ++size;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newnode = new Node(item, tail, null);
        if (tail != null) {
            tail.backward = newnode;
            tail = tail.backward;
        } else {
            tail = newnode;
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
        Deque<Integer> testDeque = new Deque<>();
        if (!testDeque.isEmpty()) {
            System.out.println("FAIL: deque should be empty");
        }
        if (testDeque.size() != 0) {
            System.out.println("FAIL: size should be 0");
        }
        testDeque.addFirst(1);
        if (testDeque.removeLast() != 1) {
            System.out.println("FAIL: last should be equal to first");
        }

        testDeque.addLast(2);
        if (testDeque.removeFirst() != 2) {
            System.out.println("FAIL: first should be equal to last");
        }

        testDeque.addLast(1);
        testDeque.addLast(2);
        testDeque.addLast(3);
        testDeque.addLast(4);
        testDeque.addLast(5);

        if (testDeque.size() != 5) {
            System.out.println("FAIL: size should be 5");
        }

        StringBuilder result = new StringBuilder("");
        while (!testDeque.isEmpty()) {
            result.append(testDeque.removeFirst());
            result.append(" ");
        }
        if (!result.toString().equals("1 2 3 4 5 ")) {
            System.out.println("FAIL: '" + result + "' should be '1 2 3 4 5 '");
        }
        if (testDeque.size() != 0) {
            System.out.println("FAIL: final size should be 0: " + testDeque.size());
        }

        testDeque.addFirst(5);
        testDeque.addFirst(4);
        testDeque.addFirst(3);
        testDeque.addFirst(2);
        testDeque.addFirst(1);

        Iterator<Integer> it = testDeque.iterator();
        result = new StringBuilder("");
        while (it.hasNext()) {
            result.append(it.next());
            result.append(" ");
        }
        if (!result.toString().equals("1 2 3 4 5 ")) {
            System.out.println("FAIL iterator: '" + result + "' should be '1 2 3 4 5 '");
        }
        if (testDeque.size() != 5) {
            System.out.println("FAIL iterator: final size should be 5: " + testDeque.size());
        }
        System.out.println("PASSED: all tests");
    }
}