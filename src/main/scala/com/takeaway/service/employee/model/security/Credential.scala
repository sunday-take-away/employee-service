package com.takeaway.service.employee.model.security

import com.takeaway.service.employee.model.Identifiable
import org.joda.time.DateTime

case class Credential(
  var id: Option[String],
  username: Option[String],
  password: Option[String],
  group: Option[String],
  created: Option[DateTime]) extends Identifiable