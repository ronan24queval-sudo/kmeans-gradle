package com.ronan.kmeans;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<double[]> data = Arrays.asList(
                new double[]{1.0, 2.0},
                new double[]{1.5, 1.8},
                new double[]{5.0, 8.0},
                new double[]{8.0, 8.0},
                new double[]{1.0, 0.6},
                new double[]{9.0, 11.0}
        );

        KMeans kmeans = new KMeans(2, 100);

        List<double[]> centroids = kmeans.fit(data);

        System.out.println("Final centroids:");
        for (double[] c : centroids) {
            System.out.println(Arrays.toString(c));
        }
    }
}
