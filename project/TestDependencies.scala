import Versions._
import sbt._

object TestDependencies {

  val testBaseDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    "org.slf4j" % "slf4j-simple" % "1.7.25" % Test
  )
	
  val testAkkaDependencies = Seq(
    "com.typesafe.akka" %% "akka-testkit" % akka_version % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akka_http_version % Test
  )
		    
  val serviceTestDependencies = testBaseDependencies ++ testAkkaDependencies
		      
}
