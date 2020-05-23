/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class MergeSort {

    public static void sort(int[] a) {
        int[] aux = new int[a.length];

        sort(a, aux, 0, a.length - 1);
    }

    public static void sortBU(int[] a) {
        int[] aux = new int[a.length];
        for (int sz = 1; sz < a.length; sz = sz + sz) {
            for (int lo = 0; lo < a.length - sz; lo += sz + sz) {
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, a.length - 1));
            }
        }
    }

    private static void sort(int[] a, int[] aux, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);

        if (a[mid + 1] >= a[mid]) {
            // optimization for partial sorted arrays.
            return;
        }

        merge(a, aux, lo, mid, hi);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        // copy array
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            }
            else if (j > hi) {
                a[k] = aux[i++];
            }
            else if (aux[j] < aux[i]) {
                a[k] = aux[j++];
            }
            else {
                a[k] = aux[i++];
            }
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int[] arr = new int[n];
        int[] arrBU = new int[n];
        for (int i = 0; i < n; i++) {
            int item = StdRandom.uniform(10000);
            arr[i] = item;
            arrBU[i] = item;
        }

        // merge sort
        Stopwatch sw = new Stopwatch();
        sort(arr);
        double sortElapsedSecs = sw.elapsedTime();

        sw = new Stopwatch();
        sortBU(arrBU);
        double sortBUElapsedSecs = sw.elapsedTime();

        checkArraySort(arr);
        checkArraySort(arrBU);

        StdOut.println("OK");
        StdOut.printf("merge sort - %.4f secs \r\n", sortElapsedSecs);
        StdOut.printf("bottom up merge sort - %.4f secs \r\n", sortBUElapsedSecs);
    }

    private static void checkArraySort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                throw new IllegalArgumentException("Incorrect sort at index " + i);
            }
        }
    }
}
