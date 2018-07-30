package com.takeaway.service.employee.model

trait Identifiable {
  var id: Option[String]

  def setId(id: Option[String]): Unit = {
    this.id = id
  }
}
