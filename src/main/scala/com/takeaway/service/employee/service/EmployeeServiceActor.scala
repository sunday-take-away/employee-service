package com.takeaway.service.employee.service

import akka.actor.{ Actor, ActorLogging, Props }
import com.takeaway.service.employee.model.Employee
import com.takeaway.service.employee.repository.EmployeeRepository
import com.takeaway.service.employee.repository.init.Repositories
import com.takeaway.service.employee.service.amqp._
import com.takeaway.service.employee.system.akka.extender.ActorContextExtender._

case class CreateEmployee(employee: Employee)
case class CreateEmployeeCompleted(employeeId: String)

case class GetEmployee(employeeId: String)
case class GetEmployeeCompleted(employeeResult: Option[Employee], employeeId: String)

case class UpdateEmployee(employee: Employee, employeeId: String)
case class UpdateEmployeeCompleted(employeeId: String)

case class DeleteEmployee(employeeId: String)
case class DeleteEmployeeCompleted(employeeId: String)

class EmployeeServiceActor extends Actor with ActorLogging {
  private[this] val repository = Repositories.getRepository[EmployeeRepository]()
  private[this] val messageQueueActor = context.lookup_existing_actor(EmployeeMessageQueueActor.name)

  implicit val ec = context.dispatcher

  def receive = {

    case CreateEmployee(employee) =>
      log.debug(s"creating new employee:'$employee'")
      val requester = sender()
      val operation = repository.create(employee)
      operation.map { employee =>
        messageQueueActor ! EmployeeCreated(employee)
        requester ! CreateEmployeeCompleted(employee.id.get)
      }

    case GetEmployee(employeeId) =>
      log.debug(s"finding employee for id:'$employeeId'")
      val requester = sender()
      val operation = repository.findById(Some(employeeId))
      operation.map(employee => requester ! GetEmployeeCompleted(employee, employeeId))

    case UpdateEmployee(employee, employeeId) =>
      log.debug(s"updating employee:'$employee'")
      val requester = sender()
      val operation = repository.update(employee)
      operation.map { employee =>
        messageQueueActor ! EmployeeUpdated(employee)
        requester ! UpdateEmployeeCompleted(employeeId)
      }

    case DeleteEmployee(employeeId) =>
      log.debug(s"deleting employee for id:'$employeeId'")
      val requester = sender()
      val operation = repository.deleteForId(employeeId)
      operation.map { deletedEmployeeId =>
        messageQueueActor ! EmployeeDeleted(deletedEmployeeId)
        requester ! DeleteEmployeeCompleted(employeeId)
      }
  }

}

object EmployeeServiceActor {
  def props() = Props[EmployeeServiceActor]
  val name = "employee-service-actor"
}