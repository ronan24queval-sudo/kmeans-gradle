package com.ronan.kmeans;

import java.util.*;

/**
 * Entry point of the K-Means clustering demonstration.
 *
 * <p>This class creates a small sample dataset, runs the K-Means algorithm,
 * and prints the resulting centroid positions.</p>
 *
 * <p>The example uses 2 clusters and a maximum of 100 iterations.</p>
 */
public class Main {

    /**
     * Main method of the program.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {

        // Example dataset (2-dimensional points)
        final List<double[]> data = Arrays.asList(
                new double[]{1.0, 2.0},
                new double[]{1.5, 1.8},
                new double[]{5.0, 8.0},
                new double[]{8.0, 8.0},
                new double[]{1.0, 0.6},
                new double[]{9.0, 11.0}
        );

        // Create the K-Means model
        final KMeans kmeans = new KMeans(2, 100);

        // Train the model and get final centroids
        final List<double[]> centroids = kmeans.fit(data);

        // Print the results
        System.out.println("Final centroids:");
        for (double[] c : centroids) {
            System.out.println(Arrays.toString(c));
        }
    }
}
