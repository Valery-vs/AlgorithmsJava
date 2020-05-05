/* *****************************************************************************
 *  Name:              Valeriy Smirnov
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        if (trials <= 0) {
            throw new IllegalArgumentException();
        }

        double[] scores = new double[trials];
        for (int i = 0; i < trials; i += 1) {
            int numberOfOpenSites = this.run(n);
            scores[i] = (double) numberOfOpenSites / (double) (n * n);
        }

        this.mean = StdStats.mean(scores);
        this.stddev = StdStats.stddev(scores);
        double confidenceFraction = (1.96 * this.stddev) / Math.sqrt(trials);
        this.confidenceLo = this.mean - confidenceFraction;
        this.confidenceHi = this.mean + confidenceFraction;
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));

        System.out.printf("mean = %f\n", stats.mean());
        System.out.printf("stddev = %f\n", stats.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(),
                          stats.confidenceHi());
    }

    private int run(int n) {
        Percolation p = new Percolation(n);

        while (!p.percolates()) {
            int index = StdRandom.uniform(n * n);
            int row = index / n;
            int col = index - row * n;
            p.open(row + 1, col + 1);
        }

        return p.numberOfOpenSites();
    }
}
