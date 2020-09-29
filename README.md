# UMaps-server

A Java Spark server that takes API calls containing start and destination points and returns information on the shortest path as a JSON response.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Java 11
Gradle
```
Best run on the [IntelliJ Ultimate IDE](https://www.jetbrains.com/idea/) by JetBrains

### Installing

A step by step series of examples that tell you how to get a development env running

1. Pull the repository

```
git clone https://github.com/johnsonkuang/UMaps-server.git
```

2. Open the root directory in IntelliJ.

3. Test whether the system has been installed properly by running the gradle task
```
gradle hw-campuspaths-server:runSpark
```

You should see the following output in red:
```
[main] INFO CampusPaths Server - Listening on: http://localhost:4567
[Thread-0] INFO org.eclipse.jetty.util.log - Logging initialized @282ms to org.eclipse.jetty.util.log.Slf4jLog
[Thread-0] WARN org.eclipse.jetty.server.AbstractConnector - Ignoring deprecated socket close linger time
[Thread-0] INFO spark.embeddedserver.jetty.EmbeddedJettyServer - == Spark has ignited ...
[Thread-0] INFO spark.embeddedserver.jetty.EmbeddedJettyServer - >> Listening on 0.0.0.0:4567
[Thread-0] INFO org.eclipse.jetty.server.Server - jetty-9.4.12.v20180830; built: 2018-08-30T13:59:14.071Z; git: 27208684755d94a92186989f695db2d7b21ebc51; jvm 11.0.6+8-LTS
[Thread-0] INFO org.eclipse.jetty.server.session - DefaultSessionIdManager workerName=node0
[Thread-0] INFO org.eclipse.jetty.server.session - No SessionScavenger set, using defaults
[Thread-0] INFO org.eclipse.jetty.server.session - node0 Scavenging every 600000ms
[Thread-0] INFO org.eclipse.jetty.server.AbstractConnector - Started ServerConnector@3dad26bd{HTTP/1.1,[http/1.1]}{0.0.0.0:4567}
[Thread-0] INFO org.eclipse.jetty.server.Server - Started @1033ms
```


## Running the tests

There are automated tests provided for both the supplemental packages: `graph` and `pathfinder`

To run tests, execute the following command:
```
gradle hw-<replace with graph or pathfinder>:test
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* Java
* Gradle

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* CSE 331
