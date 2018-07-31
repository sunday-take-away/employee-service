package com.takeaway.service.employee.http.api.doc

import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import com.takeaway.service.employee.http.route.{ EchoRoute, EmployeeRoute }
import io.swagger.models.auth.BasicAuthDefinition

object SwaggerDocService extends SwaggerHttpService {

  override val apiClasses = Set(classOf[EchoRoute], classOf[EmployeeRoute])

  override val info = Info(version = "2.0")
  override val securitySchemeDefinitions = Map("basicAuth" -> new BasicAuthDefinition())
  override val unwantedDefinitions = Seq("Function1", "Function1RequestContextFutureRouteResult")

}
