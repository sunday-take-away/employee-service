package com.takeaway.service.employee.boot

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.takeaway.service.employee.system.SystemName
import com.takeaway.service.employee.system.akka.ActorSystemLoader
import com.typesafe.config.{ Config, ConfigFactory }

import scala.concurrent.ExecutionContextExecutor

trait ServiceBoot extends App with SystemName {
  var config: Config = _
  var system: ActorSystem = _
  var executor: ExecutionContextExecutor = _
  var materializer: ActorMaterializer = _

  def init(): Unit = {
    config = ConfigFactory.load()
    val systemName = config.getString("service.system.name")
    setSystemName(systemName)
  }

  def loadActorSystem(config: Config): Unit = {
    val actorSystemLoader = new ActorSystemLoader(config)

    system = actorSystemLoader.system
    executor = actorSystemLoader.executor
    materializer = actorSystemLoader.materializer
  }

  def loadRepositories(): Unit

  def loadAkkaServices(system: ActorSystem, config: Config): Unit

  def loadHttpServer(configuration: Config, actorSystem: ActorSystem, systemExecutor: ExecutionContextExecutor, systemMaterializer: ActorMaterializer): Unit
}
