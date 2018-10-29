/* *****************************************************************************
 *  Name:    Rich Nakasato
 *  NetID:   richnakasato
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    public class RowCol {

        private int row;         // row
        private int col;         // col

        // creates a row/col
        public RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // gets row
        public int getRow() {
            return row;
        }

        // gets col
        public int getCol() {
            return col;
        }
    }

    private int[] samples;      // used to calculate mean
    private int trials;         // cache number of trials
    private int size;           // cache block edge size

    // perform trials independ experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validateConstructorArgs(n, trials);
        samples = new int[trials];
        this.trials = trials;
        size = n;
        for (int i = 0; i < trials; i++) {
            samples[i] = runPercolationTrial(n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(samples);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(samples);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 / Math.sqrt(this.trials));
    }

    // high endpint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 / Math.sqrt(this.trials));
    }

    // test client
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(10, 100);
        System.out.println("mean: " + ps.mean());
        System.out.println("lo: " + ps.confidenceLo());
        System.out.println("hi: " + ps.confidenceHi());
    }

    // validates constructor arguments
    private void validateConstructorArgs(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid constructor arguments");
        }
    }

    // runs one percolation trial of size n and returns site count
    private int runPercolationTrial(int size) {
        Percolation perc = new Percolation(size);

        int[] nodes = new int[size * size];
        for (int node = 0; node < size * size; node++) {
            nodes[node] = node;
        }
        StdRandom.shuffle(nodes);

        for (int i = 0; i < nodes.length; i++) {
            int row = getRowFromIndex(nodes[i]);
            int col = getColFromIndex(nodes[i]);
            perc.open(row, col);
            if (perc.percolates()) {
                break;
            }
        }
        return perc.numberOfOpenSites();
    }

    // converts node "index" to row
    private int getRowFromIndex(int idx) {
        return (idx / size) + 1;
    }

    // converts node "index" to col
    private int getColFromIndex(int idx) {
        return (idx % size) + 1;
    }
}
