package com.takeaway.service.employee.support

import com.takeaway.service.employee.model.Employee
import com.takeaway.service.employee.utility.DateTimeUtility._
import org.joda.time.LocalDate

trait EmployeeTestSupport {

  val testEmployeeId = "5b63d0262e40b456aa4dbeb6"

  val testEmployee = Employee(Some(testEmployeeId), Some("eugene.le.roux@mail.com"), Some("Eugene"), Some("Le Roux"), Some(new LocalDate(1977, 6, 29)), List("running", "climbing", "chess", "travel", "meeting interesting people"), Some("27.06.2018 10:33:12".to_date()))

  val emptyTestEmployee = Employee(None, None, None, None, None, List(), None)

  val testNoneCreatedEmployeeId = Employee(None, Some("april.fool@icloud.com"), Some("April"), Some("Fool"), Some(new LocalDate(1984, 4, 1)), List("hiking", "swimming"), None)
}
