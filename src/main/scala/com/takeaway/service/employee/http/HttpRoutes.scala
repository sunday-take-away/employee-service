package com.takeaway.service.employee.http

import akka.http.scaladsl.server.Directives._
import com.takeaway.service.employee.http.api.doc.SwaggerDocService
import com.takeaway.service.employee.http.header.CORSHeaderHandler
import com.takeaway.service.employee.http.route.EmployeeRoute

trait HttpRoutes extends HttpRouteBase with CORSHeaderHandler with EmployeeRoute {

  allHttpRoutes = corsHandler(allHttpRoutes ~ employeeRoutes ~ SwaggerDocService.routes)

}
