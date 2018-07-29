package com.takeaway.service.employee.http.route

import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.model.{HttpEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.takeaway.service.employee.model.Employee
import com.takeaway.service.employee.service._
import org.joda.time.LocalDate
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.Future

class EmployeeRouteSpec extends WordSpecLike with ScalatestRouteTest with Matchers with BeforeAndAfterAll with EmployeeRoute {

  "A employee http service end-point" must {

    "create employee and return employee URI" in {
      employeeServiceCall = (_) => { Future { CreateEmployeeCompleted("b303885e-d501-4bb3-b1a8-7f2b1659d685") } }

      val jsonBodyContent = HttpEntity(`application/json`, """{"email":"eugene@mail.com","firstName":"Eugene","lastName":"Le Roux","birthDay":"1977-06-29","hobbies":["jogging","climbing","chess","travel","meeting interesting people"]}""")

      Put("/employee", jsonBodyContent) ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.Created
        contentType shouldEqual `text/plain(UTF-8)`
        header("Location") shouldBe Some(Location("/employee/b303885e-d501-4bb3-b1a8-7f2b1659d685"))
      }
    }

    "update employee and return employee URI" in {
      employeeServiceCall = (_) => { Future { UpdateEmployeeCompleted("b303885e-d501-4bb3-b1a8-7f2b1659d685") } }

      val jsonBodyContent = HttpEntity(`application/json`, """{"email":"eugene.le.roux@mail.com","firstName":"Eugene","lastName":"Le Roux","birthDay":"1977-06-29","hobbies":["running","climbing","chess","travel","meeting interesting people"]}""")

      Post("/employee/b303885e-d501-4bb3-b1a8-7f2b1659d685", jsonBodyContent) ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.OK
        contentType shouldEqual `text/plain(UTF-8)`
        header("Location") shouldBe Some(Location("/employee/b303885e-d501-4bb3-b1a8-7f2b1659d685"))
      }
    }

    "delete employee should return no content" in {
      employeeServiceCall = (_) => { Future { DeleteEmployeeCompleted("b303885e-d501-4bb3-b1a8-7f2b1659d685") } }

      Delete("/employee/b303885e-d501-4bb3-b1a8-7f2b1659d685") ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.NoContent
        contentType shouldEqual `NoContentType`
      }
    }

    "get employee and return employee data" in {
      val employeeId = "b303885e-d501-4bb3-b1a8-7f2b1659d685"
      val employee = Employee(Some(employeeId), Some("eugene.le.roux@mail.com"), Some("Eugene"), Some("Le Roux"), Some(new LocalDate(1977, 6, 29)), List("running","climbing","chess","travel","meeting interesting people"))
      employeeServiceCall = (_) => { Future { GetEmployeeCompleted(Some(employee), employeeId) } }

      Get(s"/employee/$employeeId") ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.OK
        contentType shouldEqual `application/json`
        responseAs[ByteString] shouldEqual ByteString("""{"id":"b303885e-d501-4bb3-b1a8-7f2b1659d685","email":"eugene.le.roux@mail.com","firstName":"Eugene","lastName":"Le Roux","birthDay":"1977-06-29","hobbies":["running","climbing","chess","travel","meeting interesting people"]}""")
      }
    }


  }
}
