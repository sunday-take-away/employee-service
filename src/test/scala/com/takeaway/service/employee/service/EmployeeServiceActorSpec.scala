package com.takeaway.service.employee.service

import com.takeaway.service.employee.support.{ ActorSpec, EmployeeTestSupport }
import com.takeaway.service.employee.system.akka.extender.ActorSystemExtender._
import org.scalatest.tagobjects.Slow

class EmployeeServiceActorSpec extends ActorSpec with EmployeeTestSupport {

  "A employee service actor" must {

    "create new employee" taggedAs Slow in {
      val actor = system.lookup_existing_actor(EmployeeServiceActor.name)

      actor ! CreateEmployee(testNoneCreatedEmployeeId)

      expectMsgPF() {
        case CreateEmployeeCompleted(employeeId) =>
          employeeId should not be None
      }
    }

    "get existing employee" taggedAs Slow in {
      val actor = system.lookup_existing_actor(EmployeeServiceActor.name)

      actor ! GetEmployee(testEmployeeId)

      expectMsgPF() {
        case GetEmployeeCompleted(employeeResult, employeeId) =>
          employeeId should not be None
          employeeResult should not be None
          employeeResult.get.id.get shouldEqual employeeId
      }
    }

    "update existing employee" taggedAs Slow in {
      val actor = system.lookup_existing_actor(EmployeeServiceActor.name)

      val updatedEmployee = testEmployee.copy(firstName = Some("Someone"))

      actor ! UpdateEmployee(updatedEmployee, testEmployeeId)

      expectMsgPF() {
        case UpdateEmployeeCompleted(employeeId) =>
          employeeId should not be None
      }
    }

    "delete existing employee" taggedAs Slow in {
      val actor = system.lookup_existing_actor(EmployeeServiceActor.name)

      actor ! DeleteEmployee(testEmployeeId)

      expectMsgPF() {
        case DeleteEmployeeCompleted(employeeId) =>
          employeeId should not be None
      }
    }

  }

}
