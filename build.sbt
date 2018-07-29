name := "take-away-employee-service"
organization := "com.takeaway"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.6"

dependencyOverrides ++= Dependencies.dependencyOverrides

libraryDependencies ++= Dependencies.serviceDependencies

parallelExecution in Test := false
