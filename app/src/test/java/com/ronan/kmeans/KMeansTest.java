package com.ronan.kmeans;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class KMeansTest {

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
}
