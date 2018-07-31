package com.takeaway.service.employee.service.security

import com.takeaway.service.employee.support.ActorSpec
import org.scalatest.tagobjects.Slow
import com.takeaway.service.employee.system.akka.extender.ActorSystemExtender._

class CredentialActorSpec extends ActorSpec {

  "A credential service actor" must {

    "find credential for username" taggedAs Slow in {
      val actor = system.lookup_existing_actor(CredentialActor.name)

      actor ! FindCredentialForUsername("huge")

      expectMsgPF() {
        case FindCredentialForUsernameComplete(credentialResult, username) =>
          credentialResult should not be None
          username shouldEqual "huge"
      }
    }

  }

}
