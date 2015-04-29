math-utilities ![Build Status](https://va.crowbird.com/build-status/math-utilities)
==============
- Author: Andrew Kroh
- License: Apache License, Version 2.0
- Requirements: Java 1.5+

This project currently contains a single utility for
simulating Poisson processes in Java.

How do I build it?
------------------

    ./gradlew clean test jar javadoc

That will build the project using Gradle. It uses the Gradle wrapper
to automatically download the correct version of Gradle and execute the build.

How do I run the demo?
----------------------

    ./gradlew run

That will run the demo simulation of Poisson process with mean time
between events of 5 seconds.


