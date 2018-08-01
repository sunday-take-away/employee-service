import sbt.Keys._
import sbt._

name := "take-away-employee-service"
organization := "com.takeaway"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.6"

dependencyOverrides ++= Dependencies.dependencyOverrides

libraryDependencies ++= Dependencies.serviceDependencies


//test related
parallelExecution in Test := false

lazy val Slow = config("slow").extend(Test)
configs(Slow)
inConfig(Slow)(Defaults.testTasks)

testOptions in Test += Tests.Argument("-l", "org.scalatest.tags.Slow")

testOptions in Slow -= Tests.Argument("-l", "org.scalatest.tags.Slow")
testOptions in Slow += Tests.Argument("-n", "org.scalatest.tags.Slow")


// docker-ize service
enablePlugins(DockerPlugin)

dockerfile in docker := {

  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("openjdk:8-jre")
    maintainer("sunday-take-away")
    expose(8001, 8001)
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
  }

}

imageNames in docker := Seq(
  // set latest tag
  ImageName(s"${organization.value}/${name.value}:latest"),

  // set version
  ImageName(
    namespace = Some(organization.value),
    repository = name.value,
    tag = Some("v" + version.value)
  )
)