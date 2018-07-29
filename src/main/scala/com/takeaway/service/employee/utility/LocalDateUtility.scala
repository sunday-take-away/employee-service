package com.takeaway.service.employee.utility

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

object LocalDateUtility {
  val DEFAULT_DATE_VALUE_FORMAT = "yyyy-MM-dd"

  implicit class LocalDateFormatter(date: LocalDate) {
    def to_value(format: String = DEFAULT_DATE_VALUE_FORMAT): String = {
      if (date == null) return null
      DateTimeFormat.forPattern(format).print(date)
    }
  }

  implicit class StringDate(dateValue: String) {
    def to_date(format: String = DEFAULT_DATE_VALUE_FORMAT): LocalDate = {
      if (dateValue == null) return null
      val formatter = DateTimeFormat.forPattern(format)
      formatter.parseLocalDate(dateValue)
    }
  }

}
