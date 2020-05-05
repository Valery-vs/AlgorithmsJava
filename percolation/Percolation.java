/* *****************************************************************************
 *  Name:              Valeriy Smirnov
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF weightedQuickUnionTop;
    private final WeightedQuickUnionUF weightedQuickUnionFull;
    private final boolean[] openSites;
    private final int n;
    private int openSitesCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        this.openSites = new boolean[n * n + 2];
        this.weightedQuickUnionTop = new WeightedQuickUnionUF(this.openSites.length - 1);
        this.weightedQuickUnionFull = new WeightedQuickUnionUF(this.openSites.length);

        // top hidden node
        this.openSites[0] = true;

        // bottom hidden node
        this.openSites[this.openSites.length - 1] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.checkBoundaries(row, col);
        int newOpenIndex = this.getIndex(row, col);

        // open site
        if (this.openSites[newOpenIndex]) {
            return;
        }

        this.openSites[newOpenIndex] = true;
        this.openSitesCount++;

        int siblingIndex;
        // left sibling
        if (col > 1) {
            siblingIndex = newOpenIndex - 1;
            this.unionIfSiblingOpened(newOpenIndex, siblingIndex);
        }

        // right sibling
        if (col < this.n) {
            siblingIndex = newOpenIndex + 1;
            this.unionIfSiblingOpened(newOpenIndex, siblingIndex);
        }

        // top hidden sibling
        if (row == 1) {
            this.unionIfSiblingOpened(newOpenIndex, 0);
        }
        // top sibling
        else {
            siblingIndex = this.getIndex(row - 1, col);
            this.unionIfSiblingOpened(newOpenIndex, siblingIndex);
        }

        // bottom sibling
        if (row < this.n) {
            siblingIndex = this.getIndex(row + 1, col);
            this.unionIfSiblingOpened(newOpenIndex, siblingIndex);
        }
        else {
            this.weightedQuickUnionFull.union(newOpenIndex, this.openSites.length - 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        this.checkBoundaries(row, col);
        return this.openSites[this.getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        this.checkBoundaries(row, col);
        return this.weightedQuickUnionTop.find(0) == this.weightedQuickUnionTop
                .find(this.getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.weightedQuickUnionFull.find(0) == this.weightedQuickUnionFull
                .find(this.openSites.length - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system

        // repeatedly read in sites to open
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
    }

    private int getIndex(int row, int col) {
        return (row - 1) * this.n + col;
    }

    private void unionIfSiblingOpened(int newOpenIndex, int siblingIndex) {
        if (this.openSites[siblingIndex]) {
            this.weightedQuickUnionTop.union(newOpenIndex, siblingIndex);
            this.weightedQuickUnionFull.union(newOpenIndex, siblingIndex);
        }
    }

    private void checkBoundaries(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }
    }
}
