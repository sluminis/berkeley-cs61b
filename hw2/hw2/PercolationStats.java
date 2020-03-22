package hw2;

import java.util.ArrayList;
import java.util.List;
import static edu.princeton.cs.introcs.StdRandom.*;

public class PercolationStats {

    List<Double> resultList;
    private double mean = -1;
    private double stddev = -1;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("N <= 0 || T <= 0");
        resultList = new ArrayList<>();
        for (int i = 0; i < T; ++i) {
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                int row = uniform(N);
                int col = uniform(N);
                percolation.open(row, col);
            }
            resultList.add((double)percolation.numberOfOpenSites() / (N*N));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean >= 0)
            return mean;
        double sum = 0;
        for (double result : resultList) {
            sum += result;
        }
        return (mean = sum / resultList.size());
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev >= 0)
            return stddev;
        double sum = 0;
        double mean = mean();
        for (double result : resultList) {
            sum += (mean - result) * (mean - result);
        }
        return (stddev = Math.sqrt(sum / (resultList.size() - 1)));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / Math.sqrt(resultList.size());
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / Math.sqrt(resultList.size());
    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(100, 1000, new PercolationFactory());
        System.out.println(String.format("95%% confidence interval [%.4f, %.4f]",
                percolationStats.confidenceLow(), percolationStats.confidenceHigh()));
    }
}