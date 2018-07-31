package com.takeaway.service.employee.http.route

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives.{ complete, path }
import com.takeaway.service.employee.system.SystemName
import com.takeaway.service.employee.utility.DateTimeUtility._
import io.swagger.annotations._
import javax.ws.rs.Path
import org.joda.time.DateTime

@Api(value = "/echo", produces = "application/json")
@Path("/echo")
trait EchoRoute extends RouteBase with SystemName {

  val echoRoutes = getEcho

  @ApiOperation(value = "Return http echo status", httpMethod = "GET", response = classOf[String])
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return http echo status", response = classOf[String]),
    new ApiResponse(code = 500, message = "Internal server error")))
  def getEcho = {
    path("echo") {
      val dateTimeNow = DateTime.now
      val echoResponse = s"""{"serviceName":"$systemName", "echo":"${dateTimeNow.to_value()}"}"""
      complete(HttpEntity(ContentTypes.`application/json`, echoResponse))
    }
  }
}
