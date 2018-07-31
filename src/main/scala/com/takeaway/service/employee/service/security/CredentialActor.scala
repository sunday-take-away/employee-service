package com.takeaway.service.employee.service.security

import akka.actor.{ Actor, Props }
import com.takeaway.service.employee.model.security.Credential
import com.takeaway.service.employee.repository.CredentialRepository
import com.takeaway.service.employee.repository.init.Repositories

case class FindCredentialForUsername(username: String)
case class FindCredentialForUsernameComplete(credentialResult: Option[Credential], username: String)

class CredentialActor extends Actor {
  private[this] val repository = Repositories.getRepository[CredentialRepository]()

  implicit val ec = context.dispatcher

  def receive = {

    case FindCredentialForUsername(username) =>
      val requester = sender()
      val operation = repository.findByUsername(username)
      operation.map(credential => requester ! FindCredentialForUsernameComplete(credential, username))
  }
}

object CredentialActor {
  def props() = Props[CredentialActor]
  val name = "credential-actor"
}