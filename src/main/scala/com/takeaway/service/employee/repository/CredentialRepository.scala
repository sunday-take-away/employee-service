package com.takeaway.service.employee.repository

import com.takeaway.service.employee.model.security.Credential
import com.takeaway.service.employee.repository.data.codec.CredentialCodec
import com.takeaway.service.employee.repository.provider.MongoDataProvider
import org.mongodb.scala.model.Filters._

import scala.concurrent.{ ExecutionContext, Future }

class CredentialRepository(val dataProvider: MongoDataProvider) extends RepositoryBase[Credential] {
  val collection = initializeCollection("credential", new CredentialCodec)

  def findByUsername(username: String)(implicit executionContext: ExecutionContext): Future[Option[Credential]] = {
    val operation = collection.find[Credential](equal("username", username)).toFuture()
    operation.map(x => x.headOption)
  }
}
