package com.takeaway.service.employee.http

import com.takeaway.service.employee.http.route.EchoRoute

trait HttpRouteBase extends EchoRoute {

  var allHttpRoutes = echoRoutes

}
