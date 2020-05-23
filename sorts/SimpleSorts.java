/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class SimpleSorts {
    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }

            exchange(array, i, min);
        }
    }

    public static void insertionSort(int[] array, int stride) {
        for (int i = stride; i < array.length; i++) {
            for (int j = i; j > 0; j -= stride) {
                if (array[j] < array[j - 1]) {
                    exchange(array, j, j - 1);
                }
                else {
                    break;
                }
            }

        }
    }

    public static void exchange(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }

        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int[] selectionSortArr = new int[n];
        int[] insertionSortArr = new int[n];
        int[] shellSortArr = new int[n];
        for (int i = 0; i < n; i++) {
            int item = StdRandom.uniform(10000);
            selectionSortArr[i] = item;
            insertionSortArr[i] = item;
            shellSortArr[i] = item;
        }

        // selection sort
        Stopwatch sw = new Stopwatch();
        selectionSort(selectionSortArr);
        double selectionSortElapsedSecs = sw.elapsedTime();

        // insertion sort
        sw = new Stopwatch();
        insertionSort(insertionSortArr, 1);
        double insertionSortElapsedSecs = sw.elapsedTime();

        // shell sort
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1;
        }

        sw = new Stopwatch();
        while (h >= 1) {
            insertionSort(shellSortArr, h);
            h = h / 3;
        }

        double shellSortElapsedSecs = sw.elapsedTime();

        checkArraySort(selectionSortArr);
        checkArraySort(insertionSortArr);
        checkArraySort(shellSortArr);

        StdOut.println("OK");
        StdOut.printf("selection sort - %.4f secs \r\n", selectionSortElapsedSecs);
        StdOut.printf("insertion sort - %.4f secs \r\n", insertionSortElapsedSecs);
        StdOut.printf("shell sort - %.4f secs \r\n", shellSortElapsedSecs);
    }

    private static void checkArraySort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                throw new IllegalArgumentException("Incorrect sort at index " + i);
            }
        }
    }
}
