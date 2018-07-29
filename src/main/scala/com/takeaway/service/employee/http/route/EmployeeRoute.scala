package com.takeaway.service.employee.http.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import com.takeaway.service.employee.model.Employee
import com.takeaway.service.employee.service._
import com.takeaway.service.employee.system.akka.extender.ActorSystemExtender._
import io.circe.generic.auto._

import scala.concurrent.Future


trait EmployeeRoute extends RouteBase {

  var employeeServiceCall: (Any) => Future[Any] = (actorMessage: Any) => {
    system.lookup_existing_actor(EmployeeServiceActor.name) ? actorMessage
  }

  val employeeRoutes = {
    path("employee") {
      put {
        entity(as[Employee]) {
          case employee =>
            onSuccess(employeeServiceCall(CreateEmployee(employee)).mapTo[CreateEmployeeCompleted]) { result =>
              val employeeURI = s"/employee/${result.employeeId}"
              respondWithHeader(Location(employeeURI)) {
                complete(StatusCodes.Created)
              }
            }
        }
      }
    } ~
    path("employee" / Segment) { employeeId =>
      get {
        onSuccess(employeeServiceCall(GetEmployee(employeeId)).mapTo[GetEmployeeCompleted]) { result =>
          complete(result.employeeResult)
        }
      } ~
      post {
        entity(as[Employee]) {
          case employee =>
            onSuccess(employeeServiceCall(UpdateEmployee(employee, employeeId)).mapTo[UpdateEmployeeCompleted]) { result =>
              val employeeURI = s"/employee/${result.employeeId}"
              respondWithHeader(Location(employeeURI)) {
                complete(StatusCodes.OK)
              }
            }
        }
      } ~
      delete {
        onSuccess(employeeServiceCall(DeleteEmployee(employeeId)).mapTo[DeleteEmployeeCompleted]) { _ =>
            complete(StatusCodes.NoContent)
        }
      }
    }
  }

}
