package com.takeaway.service.employee.http.route

import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.model.{ HttpEntity, StatusCodes }
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.takeaway.service.employee.service._
import com.takeaway.service.employee.support.EmployeeTestSupport
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }

import scala.concurrent.Future

class EmployeeRouteSpec extends WordSpecLike with ScalatestRouteTest with Matchers with BeforeAndAfterAll with EmployeeRoute with EmployeeTestSupport {

  "A employee http service end-point" must {

    "create employee and return employee URI" in {
      employeeServiceCall = (_) => { Future { CreateEmployeeCompleted(testEmployeeId) } }

      val jsonBodyContent = HttpEntity(`application/json`, """{"email":"eugene@mail.com","firstName":"Eugene","lastName":"Le Roux","birthDay":"1977-06-29","hobbies":["jogging","climbing","chess","travel","meeting interesting people"]}""")

      Put("/employee", jsonBodyContent) ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.Created
        contentType shouldEqual `text/plain(UTF-8)`
        header("Location") shouldBe Some(Location(s"/employee/$testEmployeeId"))
      }
    }

    "update employee and return employee URI" in {
      employeeServiceCall = (_) => { Future { UpdateEmployeeCompleted(testEmployeeId) } }

      val jsonBodyContent = HttpEntity(`application/json`, """{"email":"eugene.le.roux@mail.com","firstName":"Eugene","lastName":"Le Roux","birthDay":"1977-06-29","hobbies":["running","climbing","chess","travel","meeting interesting people"]}""")

      Post(s"/employee/$testEmployeeId", jsonBodyContent) ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.OK
        contentType shouldEqual `text/plain(UTF-8)`
        header("Location") shouldBe Some(Location(s"/employee/$testEmployeeId"))
      }
    }

    "delete employee should return no content" in {
      employeeServiceCall = (_) => { Future { DeleteEmployeeCompleted(testEmployeeId) } }

      Delete(s"/employee/$testEmployeeId") ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.NoContent
        contentType shouldEqual `NoContentType`
      }
    }

    "get employee and return employee data" in {
      employeeServiceCall = (_) => { Future { GetEmployeeCompleted(Some(testEmployee), testEmployeeId) } }

      Get(s"/employee/$testEmployeeId") ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.OK
        contentType shouldEqual `application/json`
        responseAs[ByteString] shouldEqual ByteString(s"""{"id":"$testEmployeeId","email":"eugene.le.roux@mail.com","firstName":"Eugene","lastName":"Le Roux","birthDay":"1977-06-29","hobbies":["running","climbing","chess","travel","meeting interesting people"],"created":"27.06.2018 10:33:12"}""")
      }
    }

  }
}
