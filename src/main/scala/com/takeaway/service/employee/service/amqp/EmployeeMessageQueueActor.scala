package com.takeaway.service.employee.service.amqp

import akka.actor.{ Actor, ActorLogging, Props }
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.takeaway.service.employee.amqp.init.AdvancedMessagingQueues
import com.takeaway.service.employee.model.Employee

case class EmployeeCreated(employee: Employee)
case class EmployeeUpdated(employee: Employee)
case class EmployeeDeleted(employeeId: Option[String])

class EmployeeMessageQueueActor extends Actor with ActorLogging {
  private[this] val queueProvider = AdvancedMessagingQueues.getQueueProvider

  //binding to this actor context
  implicit val materializer = ActorMaterializer()

  def receive = {

    case EmployeeCreated(employee) =>
      log.debug(s"Employee CREATED $employee")
      Source(Vector(employee.toString)).map(s => ByteString(s)).runWith(queueProvider.sink())

    case EmployeeUpdated(employee) =>
      log.debug(s"Employee UPDATED $employee")

    case EmployeeDeleted(employeeId) =>
      log.debug(s"Employee DELETED $employeeId")
  }

}

object EmployeeMessageQueueActor {
  def props() = Props[EmployeeMessageQueueActor]
  val name = "employee-message-queue-actor"
}
