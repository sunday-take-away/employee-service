package com.takeaway.service.employee.http.auth

import akka.http.scaladsl.server.directives.Credentials
import akka.pattern.ask
import com.takeaway.service.employee.model.security.Credential
import com.takeaway.service.employee.service.security.{ CredentialActor, FindCredentialForUsername, FindCredentialForUsernameComplete }
import com.takeaway.service.employee.system.akka.ActorSystemAccessor
import com.takeaway.service.employee.system.akka.extender.ActorSystemExtender._

import scala.concurrent.Future

trait RouteAuthenticator extends ActorSystemAccessor {
  import system.dispatcher

  var authenticatorActorCallback: (Any) => Future[Any] = (actorMessage: Any) => {
    system.lookup_existing_actor(CredentialActor.name) ? actorMessage
  }

  def authenticatorCallback(credentials: Credentials): Future[Option[Credential]] =
    credentials match {
      case p @ Credentials.Provided(id) =>
        authenticatorActorCallback(FindCredentialForUsername(id)).map {
          case FindCredentialForUsernameComplete(credentialResult, _) =>
            credentialResult match {
              case Some(credential) => if (p.verify(credential.password.get)) Some(credential) else None
              case _ => None
            }
          case _ => None
        }
      case _ => Future.successful(None)
    }
}