import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_NUMBER = 1.96;
    private final double[] percolationThresholds;
    private final double mean;
    private final double stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0) {
            throw new IllegalArgumentException("Grid side size '" + n + "' is out of bounds");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("Number of trials '" + trials + "' is out of bounds");
        }
        percolationThresholds = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            percolationThresholds[trial] = simulate(n);
        }
        mean = StdStats.mean(percolationThresholds);
        stddev = StdStats.stddev(percolationThresholds);
    }

    private double simulate(int n) {
        Percolation percolation = new Percolation(n);
        int[] indexes = new int[n*n];
        for (int i = 0; i < n*n; ++i) {
            indexes[i] = i;
        }
        StdRandom.shuffle(indexes);
        int opened = 0;
        double total = n * n;

        for (int index : indexes) {
            int col = index % n;
            int row = (index - col) / n;
            row += 1;
            col += 1;
            percolation.open(row, col);
            opened += 1;
            if (percolation.percolates()) {
                return opened / total;
            }
        }

        return 1.0;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_NUMBER * stddev() / Math.sqrt(percolationThresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_NUMBER * stddev() / Math.sqrt(percolationThresholds.length));
    }

    // test client (described below)
    public static void main(String[] args) {
        int gridDimension = Integer.parseInt(args[0]);
        int trialsNumber = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(gridDimension, trialsNumber);
        System.out.println("mean                     = " + pStats.mean());
        System.out.println("stddev                   = " + pStats.stddev());
        System.out.println("95%% confidence interval = [" +
                pStats.confidenceLo() + ", " + pStats.confidenceHi() + "]");
    }

}
