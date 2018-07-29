package com.takeaway.service.employee.system.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.takeaway.service.employee.system.SystemName
import com.typesafe.config.Config

class ActorSystemLoader(config: Config) extends SystemName {
  implicit val system: ActorSystem = ActorSystem(systemName, config)
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  def actorSystemInterface: String = {
    config.getString("service.akka.hostname")
  }

  def actorSystemPort: Int = {
    config.getInt("service.akka.port")
  }
}
