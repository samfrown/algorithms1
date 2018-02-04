import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("usage: java Permutation <k>");
        }
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> que = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            que.enqueue(StdIn.readString());
        }

        Iterator<String> it = que.iterator();
        while (count > 0 && it.hasNext()) {
            System.out.println(it.next());
            --count;
        }

    }
}
