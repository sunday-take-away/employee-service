package com.takeaway.service.employee.http.boot

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.takeaway.service.employee.system.ConfigAccessor
import com.takeaway.service.employee.system.akka.ActorSystemAccessor

trait HttpStartup extends ActorSystemAccessor with ConfigAccessor with HttpShutdown {
  implicit val materializer: ActorMaterializer

  def startHttpServer() {
    val httpServerIp = config.getString("service.http.interface")
    val httpServerPort = config.getInt("service.http.port")

    val httpBinder = Http().bindAndHandle(httpRoutes, httpServerIp, httpServerPort)

    sys.addShutdownHook(shutdownHttpServer(httpBinder))

    println(s"HTTP server listening on http://$httpServerIp:$httpServerPort")
  }

  def httpRoutes: Route
}
