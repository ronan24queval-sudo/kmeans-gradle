# K-Means Clustering — Java + Gradle

This project implements a simple K-Means clustering algorithm in Java,
using the Gradle build tool.  
It demonstrates modern build automation practices including:

- Dependency management  
- Compilation  
- Testing  
- Packaging into a runnable JAR  
- Version management  
- Custom Gradle tasks  

## 1. Requirements

No need to install Gradle.  
The project uses the Gradle Wrapper, so everything works with:

    ./gradlew

Requires:

- Java 17 or newer

## 2. Build the project

Run the full build (compiles, tests, packages):

    ./gradlew build

Gradle will automatically:

- download dependencies  
- compile the code  
- run unit tests  
- produce the final JAR in `app/build/libs/`

## 3. Run the project

Normal execution:

    ./gradlew run

Custom task:

    ./gradlew runApp

## 4. Run the generated JAR

After building:

    java -jar app/build/libs/app-1.0.0.jar

(or similar filename)

## 5. Run tests

    ./gradlew test

## 6. Project structure

    kmeans-gradle/
     ├── app/
     │   ├── src/
     │   │   ├── main/java/com/ronan/kmeans/
     │   │   │   ├── Main.java
     │   │   │   └── KMeans.java
     │   │   └── test/java/com/ronan/kmeans/
     │   │       └── KMeansTest.java
     │   ├── build.gradle
     ├── settings.gradle
     ├── gradlew
     ├── gradlew.bat
     └── README.md

## 7. Build tool configuration

The `app/build.gradle` file contains:

- project metadata (`group`, `version`)
- external dependencies:
  - `commons-math3`
  - `junit`
- application entry point
- unit test configuration
- custom task `runApp`
- JAR packaging setup

## 8. Technologies used

- Java 17  
- Gradle 8.x (wrapper)  
- Apache Commons Math  
- JUnit Jupiter  

## 9. Documentation (Javadoc)

You can generate the reference documentation with:

./gradlew javadoc

The output will be available in:

app/build/docs/javadoc/index.html

## 10. License

This project is licensed under the **MIT License** — see the `LICENSE` file.


## 10. License

This project is licensed under the **MIT License** — see the `LICENSE` file.
