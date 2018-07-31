package com.takeaway.service.employee.service

import akka.actor.{ Actor, ActorLogging, Props }
import com.takeaway.service.employee.model.Employee
import com.takeaway.service.employee.repository.EmployeeRepository
import com.takeaway.service.employee.repository.init.Repositories

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

  implicit val ec = context.dispatcher

  def receive = {

    case CreateEmployee(employee) =>
      log.debug(s"creating employee:'$employee'")
      val requester = sender()
      val operation = repository.create(employee)
      operation.map(employee => requester ! CreateEmployeeCompleted(employee.id.get))

    case GetEmployee(employeeId) =>
      log.debug(s"finding employee for id:'$employeeId'")
      val requester = sender()
      val operation = repository.findById(Some(employeeId))
      operation.map(employee => requester ! GetEmployeeCompleted(employee, employeeId))

    case UpdateEmployee(employee, employeeId) =>
      log.debug(s"updating employee:'$employee'")
      val requester = sender()
      val operation = repository.update(employee)
      operation.map(_ => requester ! UpdateEmployeeCompleted(employeeId))

    case DeleteEmployee(employeeId) =>
      log.debug(s"deleting employee for id:'$employeeId'")
      val requester = sender()
      val operation = repository.deleteForId(employeeId)
      operation.map(_ => requester ! DeleteEmployeeCompleted(employeeId))
  }

}

object EmployeeServiceActor {
  def props() = Props[EmployeeServiceActor]
  val name = "employee-service-actor"
}