package com.takeaway.service.employee.service.init

import akka.actor.ActorSystem
import com.takeaway.service.employee.service.EmployeeServiceActor
import com.takeaway.service.employee.service.security.CredentialActor
import com.typesafe.config.Config

object ServiceActors {

  def initialize(system: ActorSystem, config: Config): Unit = {
    system.actorOf(EmployeeServiceActor.props(), EmployeeServiceActor.name)
    system.actorOf(CredentialActor.props(), CredentialActor.name)
  }

}
