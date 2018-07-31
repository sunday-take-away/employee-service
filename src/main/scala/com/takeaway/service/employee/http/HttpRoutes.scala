package com.takeaway.service.employee.http

import akka.http.scaladsl.server.Directives._
import com.takeaway.service.employee.http.api.doc.SwaggerDocService
import com.takeaway.service.employee.http.header.CORSHeaderHandler

trait HttpRoutes extends HttpRouteBase with CORSHeaderHandler {

  allHttpRoutes = corsHandler(allHttpRoutes ~ SwaggerDocService.routes)

}
