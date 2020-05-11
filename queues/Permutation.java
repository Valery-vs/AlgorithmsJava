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

        if (n == 0) {
            return;
        }

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String line = StdIn.readString();
            queue.enqueue(line);
        }

        int i = 0;
        for (String s : queue) {
            StdOut.println(s);
            i++;
            if (i >= n) {
                break;
            }
        }
    }
}
