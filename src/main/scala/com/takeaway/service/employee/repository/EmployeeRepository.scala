package com.takeaway.service.employee.repository

import com.takeaway.service.employee.model.Employee
import com.takeaway.service.employee.repository.data.codec.EmployeeCodec
import com.takeaway.service.employee.repository.data.extender.ObjectIdExtender._
import com.takeaway.service.employee.repository.data.extender.RepositoryUpdateExtender._
import com.takeaway.service.employee.repository.provider.MongoDataProvider
import org.joda.time.DateTime
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._

import scala.concurrent.{ExecutionContext, Future}

class EmployeeRepository(val dataProvider: MongoDataProvider) extends RepositoryBase[Employee] {
  val collection = initializeCollection("employee", new EmployeeCodec)

  override def create(employee: Employee)(implicit executionContext: ExecutionContext): Future[Employee] = {
    val idodized = employee.copy(id = getDefaultId(), created = Some(DateTime.now))
    super.create(idodized)
  }

  def update(employee: Employee)(implicit executionContext: ExecutionContext): Future[Employee] = {
    val updateOperations = Seq(
      employee.email.update_field("email"),
      employee.firstName.update_field("firstName"),
      employee.lastName.update_field("lastName"),
      employee.birthDay.update_field("birthDay"),
      employee.hobbies.update_field("hobbies"))
    val operation = collection.updateOne(equal("_id", employee.id.to_object_id), combine(updateOperations: _*)).toFuture()
    operation.map(_ => employee)
  }
}
