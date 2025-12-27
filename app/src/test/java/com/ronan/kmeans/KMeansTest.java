package com.ronan.kmeans;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link KMeans} class.
 *
 * <p>These tests verify that:</p>
 * <ul>
 *     <li>The K-Means algorithm runs without errors</li>
 *     <li>Initial centroids are correctly generated</li>
 *     <li>Points are correctly assigned to the closest centroid</li>
 *     <li>New centroids are correctly recomputed from clusters</li>
 * </ul>
 *
 * <p>All tests use very small datasets to validate core algorithm behavior.</p>
 */
public class KMeansTest {

    /**
     * Verifies that the K-Means algorithm completes and returns exactly k centroids.
     */
    @Test
    public void testKMeansRuns() {
        final List<double[]> data = Arrays.asList(
                new double[]{1.0, 2.0},
                new double[]{2.0, 1.0},
                new double[]{8.0, 9.0}
        );

        final KMeans k = new KMeans(2, 50);
        final List<double[]> centroids = k.fit(data);

        assertEquals(2, centroids.size());
    }

    /**
     * Tests that exactly k points are selected as initial centroids.
     */
    @Test
    public void testInitializeCentroids() {
        final KMeans k = new KMeans(2, 10);

        final List<double[]> data = Arrays.asList(
                new double[]{1, 2},
                new double[]{3, 4},
                new double[]{5, 6}
        );

        final List<double[]> centroids =
                k.initializeCentroids(new ArrayList<>(data));

        assertEquals(2, centroids.size(), "Should pick exactly k initial centroids");
    }

    /**
     * Tests that points are assigned to the correct centroid based on Euclidean distance.
     */
    @Test
    public void testAssignPointsToClusters() {
        final KMeans k = new KMeans(2, 10);

        final List<double[]> data = Arrays.asList(
                new double[]{1, 1},
                new double[]{2, 2},
                new double[]{8, 8},
                new double[]{9, 9}
        );

        final List<double[]> centroids = Arrays.asList(
                new double[]{1, 1},
                new double[]{9, 9}
        );

        final Map<double[], List<double[]>> clusters =
                k.assignPointsToClusters(data, centroids);

        assertEquals(2, clusters.size());
        assertEquals(2, clusters.get(centroids.get(0)).size());
        assertEquals(2, clusters.get(centroids.get(1)).size());
    }

    /**
     * Tests that new centroids are correctly computed as the mean of cluster points.
     */
    @Test
    public void testRecomputeCentroids() {
        final KMeans k = new KMeans(2, 10);

        final Map<double[], List<double[]>> clusters = new HashMap<>();

        clusters.put(
                new double[]{0, 0},
                Arrays.asList(
                        new double[]{1, 1},
                        new double[]{3, 3}
                )
        );

        clusters.put(
                new double[]{0, 0},
                Arrays.asList(
                        new double[]{10, 10},
                        new double[]{14, 14}
                )
        );

        final List<double[]> newCentroids = k.recomputeCentroids(clusters);

        assertArrayEquals(new double[]{2.0, 2.0}, newCentroids.get(0));
        assertArrayEquals(new double[]{12.0, 12.0}, newCentroids.get(1));
    }
}
