package com.takeaway.service.employee.service.amqp

import akka.actor.{ Actor, ActorLogging, Props }
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.takeaway.service.employee.amqp.init.AdvancedMessagingQueues
import com.takeaway.service.employee.http.serialization.JsonCirceCompleteSerialization
import com.takeaway.service.employee.model.Employee
import io.circe.generic.auto._
import io.circe.syntax._

case class EmployeeCreated(employee: Employee)
case class EmployeeUpdated(employee: Employee)
case class EmployeeDeleted(employee: Employee)

class EmployeeMessageQueueActor extends Actor with ActorLogging with JsonCirceCompleteSerialization {
  private[this] val queueProvider = AdvancedMessagingQueues.getQueueProvider

  //binding to this actor context
  implicit val materializer = ActorMaterializer()

  def receive = {

    case EmployeeCreated(employee) =>
      log.debug(s"Employee CREATED $employee")
      Source(Vector(employee)).map(e => encodeEventData(e, "CREATED")).runWith(queueProvider.sink())

    case EmployeeUpdated(employee) =>
      log.debug(s"Employee UPDATED $employee")
      Source(Vector(employee)).map(e => encodeEventData(e, "UPDATED")).runWith(queueProvider.sink())

    case EmployeeDeleted(employee) =>
      log.debug(s"Employee DELETED $employee")
      Source(Vector(employee)).map(e => encodeEventData(e, "DELETED")).runWith(queueProvider.sink())
  }

  private def encodeEventData(employee: Employee, eventPerformed: String): ByteString = {
    val employeeData = employee.asJson.noSpaces
    val metaMessage = s"EVENT_TYPE->Employee|EVENT_ID->${employee.id.getOrElse("")}|EVENT_PERFORMED->$eventPerformed|EVENT_DATA->$employeeData"
    ByteString(metaMessage, "UTF-8")
  }

}

object EmployeeMessageQueueActor {
  def props() = Props[EmployeeMessageQueueActor]
  val name = "employee-message-queue-actor"
}
