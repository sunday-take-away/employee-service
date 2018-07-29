import TestDependencies._
import Versions._
import sbt._

object Dependencies {

  val dependencyOverrides = Set(
    "com.typesafe.akka" %% "akka-actor" % akka_version,
    "com.typesafe.akka" %% "akka-stream" % akka_version
  )
	    
  val baseDependencies = Seq(
    "com.typesafe" % "config" % "1.3.3",
    "joda-time" % "joda-time" % "2.10"
  )
			
  val akkaDependencies = Seq(
    "com.typesafe.akka" %% "akka-actor" % akka_version,
    "com.typesafe.akka" %% "akka-stream" % akka_version,
    "com.typesafe.akka" %% "akka-http" % akka_http_version,
    "com.typesafe.akka" %% "akka-http-xml" % akka_http_version
  )


  val serializationDependencies = Seq(
    "io.circe" %% "circe-core" % circe_version,
    "io.circe" %% "circe-generic" % circe_version,
    "io.circe" %% "circe-parser" % circe_version
  )

  val serviceDependencies = baseDependencies ++
                            akkaDependencies ++
                            serializationDependencies ++
                            serviceTestDependencies
}
