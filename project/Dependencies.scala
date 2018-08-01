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

  val dataDependencies = Seq(
    "org.mongodb.scala" %% "mongo-scala-driver" % mongo_version
  )

  val loggingDependencies = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.akka" %% "akka-slf4j" % akka_version
  )

  val swaggerDependencies = Seq(
    "io.swagger" % "swagger-jaxrs" % "1.5.20",
    "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.14.1",
    "javax.xml.bind" % "jaxb-api" % "2.3.0"
  )

  val amqpDependencies = Seq(
    "com.lightbend.akka" %% "akka-stream-alpakka-amqp" % "0.20"
  )

  val serviceDependencies = baseDependencies ++
                            dataDependencies ++
                            akkaDependencies ++
                            loggingDependencies ++
                            serializationDependencies ++
                            swaggerDependencies ++
                            amqpDependencies ++
                            serviceTestDependencies
}
