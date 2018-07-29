package com.takeaway.service.employee.model

import org.scalatest.{Matchers, WordSpecLike}

class EmployeeSpec extends WordSpecLike with Matchers {

  "An employee" must {

    "have full name" in {
      val employee = Employee(None, None, Some("Eugene"), Some("Le Roux"), None, List())
      employee.fullName shouldEqual "Eugene Le Roux"
    }

    "have full name as empty string when first and last name not set" in {
      val employee = Employee(None, None, None, None, None, List())
      employee.fullName shouldEqual ""
    }

    "indicate each property for to string" in {
      val employee = Employee(None, None, None, None, None, List())
      employee.toString shouldEqual "id:None email:None firstName:None lastName:None birthDay:None hobbies:List()"
    }

  }
}
