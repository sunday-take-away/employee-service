package com.takeaway.service.employee.utility

object StringUtility {

  implicit class StringExtender(value: String) {
    def has_value(): Boolean = {
      value.trim.length > 0
    }
  }

  implicit class OptionStringExtender(value: Option[String]) {
    def has_value(): Boolean = {
      value.isDefined && value.getOrElse("").has_value()
    }
  }

}
