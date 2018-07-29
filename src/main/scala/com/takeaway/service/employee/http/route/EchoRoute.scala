package com.takeaway.service.employee.http.route

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives.{ complete, path }
import com.takeaway.service.employee.system.SystemName
import com.takeaway.service.employee.utility.DateTimeUtility._
import org.joda.time.DateTime

trait EchoRoute extends RouteBase with SystemName {

  val echoRoutes = {
    path("echo") {
      val dateTimeNow = DateTime.now
      val echoResponse = s"""{"serviceName":"$systemName", "echo":"${dateTimeNow.to_value()}"}"""
      complete(HttpEntity(ContentTypes.`application/json`, echoResponse))
    }
  }
}
