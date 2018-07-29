package com.takeaway.service.employee.utility

import org.joda.time.{DateTime, LocalDate}
import org.joda.time.format.DateTimeFormat

object DateTimeUtility {
  val DEFAULT_DATE_TIME_VALUE_FORMAT = "dd.MM.yyyy HH:mm:ss"


  implicit class DateTimeFormatter(dateTime: DateTime) {
    def to_value(format: String = DEFAULT_DATE_TIME_VALUE_FORMAT): String = {
      if (dateTime == null) return null
      DateTimeFormat.forPattern(format).print(dateTime)
    }
  }

  implicit class StringDateTime(dateTimeValue: String) {
    def to_date(format: String = DEFAULT_DATE_TIME_VALUE_FORMAT): DateTime = {
      if (dateTimeValue == null) return null
      val formatter = DateTimeFormat.forPattern(format)
      formatter.parseDateTime(dateTimeValue)
    }
  }

}
