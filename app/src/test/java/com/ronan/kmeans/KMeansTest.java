package com.ronan.kmeans;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class KMeansTest {

    // --- TON TEST ORIGINEL ---
    @Test
    public void testKMeansRuns() {
        List<double[]> data = Arrays.asList(
                new double[]{1.0, 2.0},
                new double[]{2.0, 1.0},
                new double[]{8.0, 9.0}
        );

        KMeans k = new KMeans(2, 50);
        List<double[]> centroids = k.fit(data);

        assertEquals(2, centroids.size());
    }

    // --- TEST 1 : initialisation correcte ---
    @Test
    public void testInitializeCentroids() {
        KMeans k = new KMeans(2, 10);

        List<double[]> data = Arrays.asList(
                new double[]{1, 2},
                new double[]{3, 4},
                new double[]{5, 6}
        );

        List<double[]> centroids = k.initializeCentroids(new ArrayList<>(data));

        assertEquals(2, centroids.size(), "Should pick exactly k initial centroids");
    }

    // --- TEST 2 : assignation aux bons clusters ---
    @Test
    public void testAssignPointsToClusters() {
        KMeans k = new KMeans(2, 10);

        List<double[]> data = Arrays.asList(
                new double[]{1, 1},
                new double[]{2, 2},
                new double[]{8, 8},
                new double[]{9, 9}
        );

        List<double[]> centroids = Arrays.asList(
                new double[]{1, 1},
                new double[]{9, 9}
        );

        Map<double[], List<double[]>> clusters = k.assignPointsToClusters(data, centroids);

        assertEquals(2, clusters.size());
        assertEquals(2, clusters.get(centroids.get(0)).size());
        assertEquals(2, clusters.get(centroids.get(1)).size());
    }

    // --- TEST 3 : recomputation correcte des centroïdes ---
    @Test
    public void testRecomputeCentroids() {
        KMeans k = new KMeans(2, 10);

        Map<double[], List<double[]>> clusters = new HashMap<>();

        clusters.put(new double[]{0, 0}, Arrays.asList(
                new double[]{1, 1},
                new double[]{3, 3}
        ));

        clusters.put(new double[]{0, 0}, Arrays.asList(
                new double[]{10, 10},
                new double[]{14, 14}
        ));

        List<double[]> newCentroids = k.recomputeCentroids(clusters);

        assertArrayEquals(new double[]{2.0, 2.0}, newCentroids.get(0));
        assertArrayEquals(new double[]{12.0, 12.0}, newCentroids.get(1));
    }
}
