package com.takeaway.service.employee.system.akka.extender

import akka.actor.{ ActorSelection, ActorSystem }
import com.takeaway.service.employee.system.akka.exception.ActorSystemNotAvailableException

object ActorSystemExtender {

  implicit class AkkaSelectionExtender(system: ActorSystem) {

    def lookup_existing_actor(actorName: String): ActorSelection = {
      if (system == null)
        throw new ActorSystemNotAvailableException(s"actor system is not loaded for actor:'$actorName', check your wiring")

      system.actorSelection(s"/user/$actorName")
    }
  }
}
