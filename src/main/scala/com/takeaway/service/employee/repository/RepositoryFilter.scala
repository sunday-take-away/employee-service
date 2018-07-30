package com.takeaway.service.employee.repository

import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters._

trait RepositoryFilter {

  def equal_trim_lower(fieldName: String, value: String): Bson = {
    equal(fieldName, value.trim.toLowerCase)
  }

}
