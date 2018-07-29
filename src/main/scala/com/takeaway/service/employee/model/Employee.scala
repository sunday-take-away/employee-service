package com.takeaway.service.employee.model

import org.joda.time.LocalDate

case class Employee (
  id: Option[String],
  email: Option[String],
  firstName: Option[String],
  lastName: Option[String],
  birthDay: Option[LocalDate],
  hobbies: List[String]
) {

  def fullName() = s"${firstName.getOrElse("")} ${lastName.getOrElse("")}".trim

  override def toString = s"id:$id email:$email firstName:$firstName lastName:$lastName birthDay:$birthDay hobbies:$hobbies"
}

