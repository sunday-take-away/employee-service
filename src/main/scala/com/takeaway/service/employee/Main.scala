package com.takeaway.service.employee

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.takeaway.service.employee.boot.ServiceBootLifeCycle
import com.takeaway.service.employee.http.HttpRoutes
import com.takeaway.service.employee.http.boot.HttpStartup
import com.takeaway.service.employee.repository.init.Repositories
import com.takeaway.service.employee.service.init.ServiceActors
import com.typesafe.config.Config

import scala.concurrent.ExecutionContextExecutor

object Main extends App with ServiceBootLifeCycle {

  override def loadRepositories(config: Config): Unit = {
    Repositories.initialize(config)
  }

  override def loadAkkaServices(system: ActorSystem, config: Config): Unit = {
    ServiceActors.initialize(system, config)
  }

  override def loadHttpServer(configuration: Config, actorSystem: ActorSystem, systemExecutor: ExecutionContextExecutor, systemMaterializer: ActorMaterializer): Unit = {
    class HttpStartupLoader extends HttpStartup with HttpRoutes {
      override implicit val config: Config = configuration
      override implicit val system: ActorSystem = actorSystem
      override implicit val executor: ExecutionContextExecutor = systemExecutor
      override implicit val materializer: ActorMaterializer = systemMaterializer

      override def httpRoutes: Route = allHttpRoutes
    }

    new HttpStartupLoader().startHttpServer()
  }

}
