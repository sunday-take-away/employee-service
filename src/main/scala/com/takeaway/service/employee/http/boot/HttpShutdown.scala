package com.takeaway.service.employee.http.boot

import akka.http.scaladsl.Http.ServerBinding
import com.takeaway.service.employee.system.akka.ActorSystemAccessor

import scala.concurrent.{ Await, ExecutionContextExecutor, Future }
import scala.concurrent.duration.Duration

trait HttpShutdown extends ActorSystemAccessor {
  implicit val executor: ExecutionContextExecutor

  def shutdownHttpServer(httpBinder: Future[ServerBinding]) {
    httpBinder
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate())

    Await.result(system.whenTerminated, Duration.Inf)
  }
}
