name := "take-away-employee-service"
organization := "com.takeaway"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.6"

dependencyOverrides ++= Dependencies.dependencyOverrides

libraryDependencies ++= Dependencies.serviceDependencies


parallelExecution in Test := false

lazy val Slow = config("slow").extend(Test)
configs(Slow)
inConfig(Slow)(Defaults.testTasks)

testOptions in Test += Tests.Argument("-l", "org.scalatest.tags.Slow")

testOptions in Slow -= Tests.Argument("-l", "org.scalatest.tags.Slow")
testOptions in Slow += Tests.Argument("-n", "org.scalatest.tags.Slow")