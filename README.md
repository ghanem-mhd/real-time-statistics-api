# Real-time Statistics Api

[![Build](https://github.com/ghanem-mhd/real-time-statistics-api/actions/workflows/maven.yml/badge.svg)](https://github.com/ghanem-mhd/real-time-statistics-api/actions/workflows/maven.yml)

This is an Spring Boot API for statistics. The main use case for the API is to calculate real-time statistics for the last 60 seconds of transactions.

The API have the following endpoints:

-  ```POST /transactions``` – called every time a transaction is made. It is also the sole input of this rest API.
-  ```GET /statistics``` – returns the statistic based on the transactions of the last 60 seconds.
-  ```DELETE /transactions``` – deletes all transactions.

## Requirements

For building and running the application you need:

- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.example.realtimestatisticsapi.RealTimeStatisticsApiApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
Your app should now be running on [localhost:8080](http://localhost:8080/).

## Running unit tests

```shell
mvn test clean
```

## Deploying to Heroku
Make sure you have the [Heroku CLI](https://cli.heroku.com/) installed.

```
$ heroku create
$ git push heroku main
$ heroku open
```
or

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Live Demo on Heroku

https://real-time-statistics-api.herokuapp.com/



