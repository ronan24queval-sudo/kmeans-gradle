package com.ronan.kmeans;

import java.util.*;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KMeans {

    private static final Logger logger = LoggerFactory.getLogger(KMeans.class);

    private int k;
    private int maxIterations;
    private EuclideanDistance distance = new EuclideanDistance();

    public KMeans(int k, int maxIterations) {
        this.k = k;
        this.maxIterations = maxIterations;
    }

    public List<double[]> fit(List<double[]> data) {

        logger.info("Starting K-Means with k = {}, maxIterations = {} and dataset size = {}", 
                k, maxIterations, data.size());

        List<double[]> centroids = initializeCentroids(data);
        logger.debug("Initial centroids selected: {}", Arrays.deepToString(centroids.toArray()));

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            logger.debug("Iteration {} - assigning points to clusters", iteration);

            Map<double[], List<double[]>> clusters = assignPointsToClusters(data, centroids);

            // Logging cluster sizes
            for (Map.Entry<double[], List<double[]>> entry : clusters.entrySet()) {
                logger.debug("Cluster with centroid {} has {} points", 
                        Arrays.toString(entry.getKey()), entry.getValue().size());
            }

            List<double[]> newCentroids = recomputeCentroids(clusters);
            logger.debug("New centroids computed: {}", Arrays.deepToString(newCentroids.toArray()));

            if (centroids.equals(newCentroids)) {
                logger.info("Convergence reached at iteration {}", iteration);
                break;
            }

            centroids = newCentroids;
        }

        logger.info("K-Means finished. Final centroids: {}", Arrays.deepToString(centroids.toArray()));
        return centroids;
    }

    private List<double[]> initializeCentroids(List<double[]> data) {
        Collections.shuffle(data);
        List<double[]> initial = new ArrayList<>(data.subList(0, k));
        logger.debug("Centroids initialized randomly: {}", Arrays.deepToString(initial.toArray()));
        return initial;
    }

    private Map<double[], List<double[]>> assignPointsToClusters(List<double[]> data, List<double[]> centroids) {
        Map<double[], List<double[]>> clusters = new HashMap<>();

        for (double[] centroid : centroids) {
            clusters.put(centroid, new ArrayList<>());
        }

        for (double[] point : data) {

            double[] closest = null;
            double minDistance = Double.MAX_VALUE;

            for (double[] c : centroids) {
                double d = distance.compute(point, c);
                if (d < minDistance) {
                    minDistance = d;
                    closest = c;
                }
            }

            if (closest == null) {
                logger.error("Could not assign point {} to any cluster!", Arrays.toString(point));
            }

            clusters.get(closest).add(point);
        }

        return clusters;
    }

    private List<double[]> recomputeCentroids(Map<double[], List<double[]>> clusters) {
        List<double[]> newCentroids = new ArrayList<>();

        for (Map.Entry<double[], List<double[]>> entry : clusters.entrySet()) {

            List<double[]> cluster = entry.getValue();

            if (cluster.isEmpty()) {
                logger.warn("Cluster with centroid {} is empty. Reusing previous centroid.", 
                        Arrays.toString(entry.getKey()));
                newCentroids.add(entry.getKey());
                continue;
            }

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
