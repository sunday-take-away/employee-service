package com.takeaway.service.employee.support

import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestKit }
import com.takeaway.service.employee.repository.init.Repositories
import com.takeaway.service.employee.service.init.ServiceActors
import com.typesafe.config.ConfigFactory
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }

import scala.concurrent.Await
import scala.concurrent.duration.Duration

abstract class ActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender with Matchers with WordSpecLike with BeforeAndAfterAll {

  def this() = this(ActorSystem())

  override def beforeAll() {
    val config = ConfigFactory.load()
    Repositories.initialize(config)
    ServiceActors.initialize(system, config)
  }

  override def afterAll {
    terminateActorSystem()
  }

  protected def terminateActorSystem() {
    system.terminate()
    Await.result(system.whenTerminated, Duration.Inf)
  }
}
