package com.takeaway.service.employee.model

import com.takeaway.service.employee.support.EmployeeTestSupport
import org.scalatest.{ Matchers, WordSpecLike }

class EmployeeSpec extends WordSpecLike with Matchers with EmployeeTestSupport {

  "An employee" must {

    "have full name" in {
      val employee = Employee(None, None, Some("Eugene"), Some("Le Roux"), None, List(), None)
      employee.fullName shouldEqual "Eugene Le Roux"
    }

    "have full name as empty string when first and last name not set" in {
      emptyTestEmployee.fullName shouldEqual ""
    }

    "indicate each property for to string" in {
      emptyTestEmployee.toString shouldEqual "id:None email:None firstName:None lastName:None birthDay:None hobbies:List() created:None"
    }

  }
}
