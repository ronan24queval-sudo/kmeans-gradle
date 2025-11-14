package com.ronan.kmeans;

import java.util.*;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

public class KMeans {

    private int k;
    private int maxIterations;
    private EuclideanDistance distance = new EuclideanDistance();

    public KMeans(int k, int maxIterations) {
        this.k = k;
        this.maxIterations = maxIterations;
    }

    public List<double[]> fit(List<double[]> data) {
        List<double[]> centroids = initializeCentroids(data);

        for (int i = 0; i < maxIterations; i++) {
            Map<double[], List<double[]>> clusters = assignPointsToClusters(data, centroids);
            List<double[]> newCentroids = recomputeCentroids(clusters);

            if (centroids.equals(newCentroids)) break;
            centroids = newCentroids;
        }

        return centroids;
    }

    private List<double[]> initializeCentroids(List<double[]> data) {
        Collections.shuffle(data);
        return new ArrayList<>(data.subList(0, k));
    }

    private Map<double[], List<double[]>> assignPointsToClusters(List<double[]> data, List<double[]> centroids) {
        Map<double[], List<double[]>> clusters = new HashMap<>();

        for (double[] centroid : centroids) {
            clusters.put(centroid, new ArrayList<>());
        }

        for (double[] point : data) {
            double[] closest = centroids.get(0);
            double minDistance = distance.compute(point, closest);

            for (double[] c : centroids) {
                double d = distance.compute(point, c);
                if (d < minDistance) {
                    minDistance = d;
                    closest = c;
                }
            }

            clusters.get(closest).add(point);
        }

        return clusters;
    }

    private List<double[]> recomputeCentroids(Map<double[], List<double[]>> clusters) {
        List<double[]> newCentroids = new ArrayList<>();

        for (List<double[]> cluster : clusters.values()) {
            int dim = cluster.get(0).length;
            double[] mean = new double[dim];

            for (double[] point : cluster) {
                for (int i = 0; i < dim; i++) {
                    mean[i] += point[i];
                }
            }

            for (int i = 0; i < dim; i++) {
                mean[i] /= cluster.size();
            }

            newCentroids.add(mean);
        }

        return newCentroids;
    }
}
