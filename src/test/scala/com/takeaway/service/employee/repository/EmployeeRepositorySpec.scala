package com.takeaway.service.employee.repository

import com.takeaway.service.employee.support.{ DatabaseBaseSpec, EmployeeTestSupport }
import com.takeaway.service.employee.utility.LocalDateUtility._
import org.scalatest.tagobjects.Slow

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class EmployeeRepositorySpec extends DatabaseBaseSpec with EmployeeTestSupport {
  lazy val repository = new EmployeeRepository(dataProvider)

  "A employee repository" must {

    "create new job in database" taggedAs Slow in {
      val created = repository.create(testEmployee)

      //blocking only for testing
      val result = Await.result(created, 1.second)

      result.email.get shouldEqual "eugene.le.roux@mail.com"
      result.firstName.get shouldEqual "Eugene"
      result.lastName.get shouldEqual "Le Roux"
      result.birthDay.get.to_value() shouldEqual "1977-06-29"
      result.hobbies shouldEqual List("running", "climbing", "chess", "travel", "meeting interesting people")
      result.created should not be None
      result.id should not be None
    }

  }
}
