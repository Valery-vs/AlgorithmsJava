/* *****************************************************************************
 *  Name: Valeriy Smirnov
 *  Date: 05/10/2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String line = StdIn.readString();
            queue.enqueue(line);
        }

        for (int i = 0; i < n; i++) {
            StdOut.println(queue.sample());
        }
    }
}
