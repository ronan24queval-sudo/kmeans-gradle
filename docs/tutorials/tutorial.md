# Tutorial — Running the K-Means Clustering Project

This short tutorial explains how to download, build, and run the K-Means clustering implementation included in this repository.

The project is written in Java and built using Gradle. No manual installation of Gradle is required.


## 1. Getting the project

Clone the repository:


git clone https://github.com/ronan24queval-sudo/kmeans-gradle.git
cd kmeans-gradle

## 2. Project requirements

Java 17 or newer

No need to install Gradle (Gradle Wrapper included)

To check Java version:

java -version

## 3. Build the project

Use the Gradle Wrapper:

./gradlew build

This command:

- downloads dependencies
- compiles the code
- runs tests
- packages the application

## 4. Running the K-Means program

Run directly with Gradle:

./gradlew run

Or run the custom task:

./gradlew runApp

## 5. Running the JAR manually

After building the project, the JAR is located in:

app/build/libs/

Run it with:

java -jar app/build/libs/app-1.0.0.jar
(The JAR file name may vary depending on the project version.)

## 6. What the program does

The main application (Main.java) performs:

Creation of a small example dataset

Execution of the K-Means algorithm with k = 2

Printing of the final centroids

Example output:

Final centroids:
[1.5, 1.8]
[9.2, 9.5]

## 7. Modifying the input dataset

To try your own data:

Open Main.java

Replace the list data = Arrays.asList(...) with your own points

Re-run the program

Example format (2D points):

List<double[]> data = Arrays.asList(
    new double[]{3.0, 5.2},
    new double[]{2.1, 1.9},
    new double[]{8.0, 9.0}
);

## 8. Regenerating documentation (optional)

You can rebuild the Javadoc reference documentation with:

./gradlew javadoc

The HTML output is located in:

app/build/docs/javadoc/index.html
