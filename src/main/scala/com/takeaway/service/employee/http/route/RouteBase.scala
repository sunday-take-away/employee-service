package com.takeaway.service.employee.http.route

import com.takeaway.service.employee.http.serialization.JsonCirceCompleteSerialization
import com.takeaway.service.employee.system.akka.ActorSystemAccessor

trait RouteBase extends ActorSystemAccessor with JsonCirceCompleteSerialization