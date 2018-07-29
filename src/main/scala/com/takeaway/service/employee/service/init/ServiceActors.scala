package com.takeaway.service.employee.service.init

import akka.actor.ActorSystem
import com.takeaway.service.employee.service.EmployeeServiceActor
import com.typesafe.config.Config

object ServiceActors {

  def initialize(system: ActorSystem, config: Config): Unit = {
    system.actorOf(EmployeeServiceActor.props(), EmployeeServiceActor.name)
  }

}
