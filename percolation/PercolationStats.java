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

    private static final double CON95 = 1.96;   // 95 confidence interval
    private static final int OFFSET = 1;        // offset

    private final int edgeSize;       // cache block edge size
    private final double mean;        // mean
    private final double stddev;      // std dev
    private final double conlo;       // confidence low
    private final double conhi;       // confidence hi

    // perform trials independ experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validateConstructorArgs(n, trials);
        double[] samples = new double[trials];
        edgeSize = n;
        for (int i = 0; i < trials; i++) {
            samples[i] = runPercolationTrial(n) / Math.pow(n, 2);
        }
        mean = StdStats.mean(samples);
        stddev = StdStats.stddev(samples);
        double con = (CON95 * stddev) / Math.sqrt(trials);
        conlo = mean - con;
        conhi = mean + con;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return conlo;
    }

    // high endpint of 95% confidence interval
    public double confidenceHi() {
        return conhi;
    }

    // test client
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(2, 10000);
        System.out.println("mean: " + ps.mean());
        System.out.println("stddev: " + ps.stddev());
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
        return (idx / edgeSize) + OFFSET;
    }

    // converts node "index" to col
    private int getColFromIndex(int idx) {
        return (idx % edgeSize) + OFFSET;
    }
}
