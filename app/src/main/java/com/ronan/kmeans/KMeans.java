package com.ronan.kmeans;

import java.util.*;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the K-Means clustering algorithm.
 *
 * <p>This class supports clustering multidimensional points represented as
 * {@code double[]} arrays. It performs iterative centroid recomputation
 * until convergence or until a maximum number of iterations is reached.</p>
 *
 * <p>The algorithm proceeds through the following steps:</p>
 * <ol>
 *   <li>Randomly initialize the cluster centroids</li>
 *   <li>Assign each point to the closest centroid</li>
 *   <li>Recompute centroids based on assigned points</li>
 *   <li>Repeat until convergence or iteration limit</li>
 * </ol>
 *
 * <p>Logging is performed via SLF4J at INFO and DEBUG levels to
 * visualize algorithm evolution during execution.</p>
 */
public class KMeans {

    private static final Logger logger = LoggerFactory.getLogger(KMeans.class);

    private int k;
    private int maxIterations;
    private final EuclideanDistance distance = new EuclideanDistance();

    /**
     * Creates a new K-Means clustering model.
     *
     * @param k number of clusters to generate
     * @param maxIterations maximum number of iterations before stopping
     */
    public KMeans(int k, int maxIterations) {
        this.k = k;
        this.maxIterations = maxIterations;
    }

    /**
     * Runs the K-Means algorithm on the provided dataset.
     *
     * @param data list of points, where each point is represented as {@code double[]}
     * @return a list of final centroid positions after convergence
     */
    public List<double[]> fit(List<double[]> data) {

        logger.info(
                "Starting K-Means with k = {}, maxIterations = {} and dataset size = {}",
                k, maxIterations, data.size()
        );

        List<double[]> centroids = initializeCentroids(data);
        logger.debug("Initial centroids selected: {}", Arrays.deepToString(centroids.toArray()));

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            logger.debug("Iteration {} - assigning points to clusters", iteration);

            final Map<double[], List<double[]>> clusters =
                    assignPointsToClusters(data, centroids);

            // Logging cluster sizes
            for (Map.Entry<double[], List<double[]>> entry : clusters.entrySet()) {
                logger.debug(
                        "Cluster with centroid {} has {} points",
                        Arrays.toString(entry.getKey()),
                        entry.getValue().size()
                );
            }

            final List<double[]> newCentroids = recomputeCentroids(clusters);
            logger.debug(
                    "New centroids computed: {}",
                    Arrays.deepToString(newCentroids.toArray())
            );

            if (centroids.equals(newCentroids)) {
                logger.info("Convergence reached at iteration {}", iteration);
                break;
            }

            centroids = newCentroids;
        }

        logger.info(
                "K-Means finished. Final centroids: {}",
                Arrays.deepToString(centroids.toArray())
        );
        return centroids;
    }

    // -----------------------------------------------------------
    // Package-private methods for testing
    // -----------------------------------------------------------

    /**
     * Randomly selects {@code k} points from the dataset to serve as initial centroids.
     *
     * @param data input dataset
     * @return a list of initial centroids
     */
    List<double[]> initializeCentroids(List<double[]> data) {
        Collections.shuffle(data);
        final List<double[]> initial = new ArrayList<>(data.subList(0, k));
        logger.debug("Centroids initialized randomly: {}", Arrays.deepToString(initial.toArray()));
        return initial;
    }

    /**
     * Assigns each point in the dataset to the closest centroid.
     *
     * @param data list of points
     * @param centroids current centroid positions
     * @return a mapping between each centroid and the points assigned to it
     */
    Map<double[], List<double[]>> assignPointsToClusters(
            List<double[]> data,
            List<double[]> centroids
    ) {
        final Map<double[], List<double[]>> clusters = new HashMap<>();

        for (double[] centroid : centroids) {
            clusters.put(centroid, new ArrayList<>());
        }

        for (double[] point : data) {

            double[] closest = null;
            double minDistance = Double.MAX_VALUE;

            for (double[] c : centroids) {
                final double d = distance.compute(point, c);
                if (d < minDistance) {
                    minDistance = d;
                    closest = c;
                }
            }

            if (closest == null) {
                logger.error(
                        "Could not assign point {} to any cluster!",
                        Arrays.toString(point)
                );
                continue;
            }

            clusters.get(closest).add(point);
        }

        return clusters;
    }

    /**
     * Recomputes centroids by computing the mean of all points in each cluster.
     *
     * @param clusters map where each key is a centroid and each value is its assigned points
     * @return the list of updated centroid positions
     */
    List<double[]> recomputeCentroids(Map<double[], List<double[]>> clusters) {
        final List<double[]> newCentroids = new ArrayList<>();

        for (Map.Entry<double[], List<double[]>> entry : clusters.entrySet()) {

            final List<double[]> cluster = entry.getValue();

            if (cluster.isEmpty()) {
                logger.warn(
                        "Cluster with centroid {} is empty. Reusing previous centroid.",
                        Arrays.toString(entry.getKey())
                );
                newCentroids.add(entry.getKey());
                continue;
            }

            final int dim = cluster.get(0).length;
            final double[] mean = new double[dim];

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
