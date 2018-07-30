package com.takeaway.service.employee.support

import com.takeaway.service.employee.model.Employee
import com.takeaway.service.employee.utility.DateTimeUtility._
import org.joda.time.LocalDate

trait EmployeeTestSupport {

  val testEmployeeId = "b303885e-d501-4bb3-b1a8-7f2b1659d685"

  val testEmployee = Employee(Some(testEmployeeId), Some("eugene.le.roux@mail.com"), Some("Eugene"), Some("Le Roux"), Some(new LocalDate(1977, 6, 29)), List("running", "climbing", "chess", "travel", "meeting interesting people"), Some("27.06.2018 10:33:12".to_date()))

  val emptyTestEmployee = Employee(None, None, None, None, None, List(), None)
}
