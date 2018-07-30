package com.takeaway.service.employee.service

import akka.actor.{ Actor, ActorLogging, Props }
import com.takeaway.service.employee.model.Employee

case class CreateEmployee(employee: Employee)
case class CreateEmployeeCompleted(employeeId: String)

case class GetEmployee(employeeId: String)
case class GetEmployeeCompleted(employeeResult: Option[Employee], employeeId: String)

case class UpdateEmployee(employee: Employee, employeeId: String)
case class UpdateEmployeeCompleted(employeeId: String)

case class DeleteEmployee(employeeId: String)
case class DeleteEmployeeCompleted(employeeId: String)

class EmployeeServiceActor extends Actor with ActorLogging {

  def receive = {

    case CreateEmployee(employee) =>
      log.debug(s"creating employee:'$employee'")
  }

}

object EmployeeServiceActor {
  def props() = Props[EmployeeServiceActor]
  val name = "employee-service-actor"
}