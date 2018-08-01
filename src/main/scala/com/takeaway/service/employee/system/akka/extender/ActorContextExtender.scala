package com.takeaway.service.employee.system.akka.extender

import akka.actor.{ ActorContext, ActorSelection }

object ActorContextExtender {

  implicit class AkkaSelectionExtender(context: ActorContext) {

    def lookup_existing_actor(actorName: String): ActorSelection = {
      context.actorSelection(s"/user/$actorName")
    }
  }
}
