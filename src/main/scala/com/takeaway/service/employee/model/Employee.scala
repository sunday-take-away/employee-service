package com.takeaway.service.employee.model

import org.joda.time.{ DateTime, LocalDate }

case class Employee(
  var id: Option[String],
  email: Option[String],
  firstName: Option[String],
  lastName: Option[String],
  birthDay: Option[LocalDate],
  hobbies: List[String],
  created: Option[DateTime]) extends Identifiable {

  def fullName() = s"${firstName.getOrElse("")} ${lastName.getOrElse("")}".trim

  override def toString = s"id:$id email:$email firstName:$firstName lastName:$lastName birthDay:$birthDay hobbies:$hobbies created:$created"
}

