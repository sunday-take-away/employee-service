package com.takeaway.service.employee.http.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import com.takeaway.service.employee.http.auth.RouteAuthenticator
import com.takeaway.service.employee.model.Employee
import com.takeaway.service.employee.service._
import com.takeaway.service.employee.system.akka.extender.ActorSystemExtender._
import io.circe.generic.auto._
import io.swagger.annotations._
import javax.ws.rs.Path

import scala.concurrent.Future

@Api(value = "/employee", produces = "application/json")
@Path("/employee")
trait EmployeeRoute extends RouteBase with RouteAuthenticator {

  var employeeServiceCall: (Any) => Future[Any] = (actorMessage: Any) => {
    system.lookup_existing_actor(EmployeeServiceActor.name) ? actorMessage
  }

  val employeeRoutes = createEmployee ~ getEmployee ~ updateEmployee ~ deleteEmployee

  @ApiOperation(value = "Create employee", httpMethod = "PUT", response = classOf[String], authorizations = Array(new Authorization(value = "basicAuth")))
  @ApiImplicitParams(Array(new ApiImplicitParam(name = "employee", value = "employee", required = true, dataTypeClass = classOf[Employee], paramType = "body")))
  @ApiResponses(Array(
    new ApiResponse(code = 201, message = "Return employee URI", response = classOf[String]),
    new ApiResponse(code = 500, message = "Internal server error")))
  def createEmployee = {
    path("employee") {
      put {
        authenticateBasicAsync(realm = "secure", authenticatorCallback) { credential =>
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
      }
    }
  }

  @Path("/{employeeId}")
  @ApiOperation(value = "Get employee", httpMethod = "GET", response = classOf[Employee])
  @ApiImplicitParams(Array(new ApiImplicitParam(name = "employeeId", value = "Id of the employee", required = true, dataType = "string", paramType = "path")))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return employee entity", response = classOf[Employee]),
    new ApiResponse(code = 500, message = "Internal server error")))
  def getEmployee = {
    path("employee" / Segment) { employeeId =>
      get {
        onSuccess(employeeServiceCall(GetEmployee(employeeId)).mapTo[GetEmployeeCompleted]) { result =>
          complete(result.employeeResult)
        }
      }
    }
  }

  @Path("/{employeeId}")
  @ApiOperation(value = "Update employee", httpMethod = "POST", response = classOf[String], authorizations = Array(new Authorization(value = "basicAuth")))
  @ApiImplicitParams(Array(new ApiImplicitParam(name = "employee", value = "employee", required = true, dataTypeClass = classOf[Employee], paramType = "body")))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return employee URI", response = classOf[String]),
    new ApiResponse(code = 500, message = "Internal server error")))
  def updateEmployee = {
    path("employee" / Segment) { employeeId =>
      post {
        authenticateBasicAsync(realm = "secure", authenticatorCallback) { credential =>
          entity(as[Employee]) {
            case employee =>
              onSuccess(employeeServiceCall(UpdateEmployee(employee, employeeId)).mapTo[UpdateEmployeeCompleted]) { result =>
                val employeeURI = s"/employee/${result.employeeId}"
                respondWithHeader(Location(employeeURI)) {
                  complete(StatusCodes.OK)
                }
              }
          }
        }
      }
    }
  }

  @Path("/{employeeId}")
  @ApiOperation(value = "Delete employee", httpMethod = "DELETE", authorizations = Array(new Authorization(value = "basicAuth")))
  @ApiImplicitParams(Array(new ApiImplicitParam(name = "employeeId", value = "Id of the employee", required = true, dataType = "string", paramType = "path")))
  @ApiResponses(Array(new ApiResponse(code = 500, message = "Internal server error")))
  def deleteEmployee = {
    path("employee" / Segment) { employeeId =>
      delete {
        authenticateBasicAsync(realm = "secure", authenticatorCallback) { credential =>
          onSuccess(employeeServiceCall(DeleteEmployee(employeeId)).mapTo[DeleteEmployeeCompleted]) { _ =>
            complete(StatusCodes.NoContent)
          }
        }
      }
    }
  }

}