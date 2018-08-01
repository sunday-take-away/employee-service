package com.takeaway.service.employee.support

import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestKit }
import com.takeaway.service.employee.repository.init.Repositories
import com.takeaway.service.employee.service.init.ServiceActors
import com.typesafe.config.{ Config, ConfigFactory }
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }

import scala.concurrent.Await
import scala.concurrent.duration.Duration

abstract class ActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender with Matchers with WordSpecLike with BeforeAndAfterAll {
  var config: Config = _

  def this() = this(ActorSystem())

  def loadConfig(): Unit = {
    config = ConfigFactory.load()
  }

  override def beforeAll() {
    loadConfig()
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
