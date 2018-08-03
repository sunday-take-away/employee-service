package com.takeaway.service.employee.http.boot

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import com.takeaway.service.employee.system.ConfigAccessor
import com.takeaway.service.employee.system.akka.ActorSystemAccessor

trait HttpStartup extends ActorSystemAccessor with ConfigAccessor with HttpShutdown {
  implicit val materializer: ActorMaterializer

  implicit def customRejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case AuthorizationFailedRejection =>
          complete((Forbidden, "Access Denied, computer says no"))
      }
      .handleAll[MethodRejection] { methodRejections =>
        val names = methodRejections.map(_.supported.name)
        complete((MethodNotAllowed, s"Method rejected, supported: ${names mkString " or "}!"))
      }
      .handle {
        case MissingQueryParamRejection(param) =>
          complete(HttpResponse(BadRequest, entity = s"Missing Parameter required $param was not found."))
      }
      .handleNotFound {
        complete((NotFound, "Not Found"))
      }
      .result()

  def startHttpServer() {
    val httpServerIp = config.getString("service.http.interface")
    val httpServerPort = config.getInt("service.http.port")

    val httpBinder = Http().bindAndHandle(httpRoutes, httpServerIp, httpServerPort)

    sys.addShutdownHook(shutdownHttpServer(httpBinder))

    println(s"HTTP server listening on http://$httpServerIp:$httpServerPort")
  }

  def httpRoutes: Route
}
