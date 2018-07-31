package com.takeaway.service.employee.http.route

import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.headers.{ BasicHttpCredentials, Location, _ }
import akka.http.scaladsl.model.{ HttpEntity, StatusCodes }
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.takeaway.service.employee.service._
import com.takeaway.service.employee.service.security.FindCredentialForUsernameComplete
import com.takeaway.service.employee.support.{ CredentialTestSupport, EmployeeTestSupport }
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }

import scala.concurrent.Future

class EmployeeRouteSpec extends WordSpecLike with ScalatestRouteTest with Matchers with BeforeAndAfterAll with EmployeeRoute with EmployeeTestSupport with CredentialTestSupport {

  authenticatorActorCallback = (_) => { Future { FindCredentialForUsernameComplete(Some(testCredential), testCredential.username.get) } }

  val validCredentials = BasicHttpCredentials(testCredential.username.get, testCredential.password.get)

  "A employee http service end-point" must {

    "create employee and return employee URI" in {
      employeeServiceCall = (_) => { Future { CreateEmployeeCompleted(testEmployeeId) } }

      val jsonBodyContent = HttpEntity(`application/json`, """{"email":"eugene@mail.com","firstName":"Eugene","lastName":"Le Roux","birthDay":"1977-06-29","hobbies":["jogging","climbing","chess","travel","meeting interesting people"]}""")

      Put("/employee", jsonBodyContent) ~> addCredentials(validCredentials) ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.Created
        contentType shouldEqual `text/plain(UTF-8)`
        header("Location") shouldBe Some(Location(s"/employee/$testEmployeeId"))
      }
    }

    "be un-authorized when not authenticated when trying to create" in {
      Put("/employee") ~> Route.seal(employeeRoutes) ~> check {
        status shouldEqual StatusCodes.Unauthorized
        header[`WWW-Authenticate`].get.challenges.head shouldEqual HttpChallenges.basic("secure")
      }
    }

    "be un-authorized when credentials are invalid when trying to create" in {
      val invalidCredentials = BasicHttpCredentials("some", "cracker")
      Put("/employee") ~> addCredentials(invalidCredentials) ~> Route.seal(employeeRoutes) ~> check {
        status shouldEqual StatusCodes.Unauthorized
        header[`WWW-Authenticate`].get.challenges.head shouldEqual HttpChallenges.basic("secure")
      }
    }

    "be un-authorized when password is invalid when trying to create" in {
      val invalidPassword = BasicHttpCredentials(testCredential.username.get, "bad-password")
      Put("/employee") ~> addCredentials(invalidPassword) ~> Route.seal(employeeRoutes) ~> check {
        status shouldEqual StatusCodes.Unauthorized
        header[`WWW-Authenticate`].get.challenges.head shouldEqual HttpChallenges.basic("secure")
      }
    }

    "update employee and return employee URI" in {
      employeeServiceCall = (_) => { Future { UpdateEmployeeCompleted(testEmployeeId) } }

      val jsonBodyContent = HttpEntity(`application/json`, """{"email":"eugene.le.roux@mail.com","firstName":"Eugene","lastName":"Le Roux","birthDay":"1977-06-29","hobbies":["running","climbing","chess","travel","meeting interesting people"]}""")

      Post(s"/employee/$testEmployeeId", jsonBodyContent) ~> addCredentials(validCredentials) ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.OK
        contentType shouldEqual `text/plain(UTF-8)`
        header("Location") shouldBe Some(Location(s"/employee/$testEmployeeId"))
      }
    }

    "be un-authorized when not authenticated when trying to update" in {
      Post(s"/employee/$testEmployeeId") ~> Route.seal(employeeRoutes) ~> check {
        status shouldEqual StatusCodes.Unauthorized
        header[`WWW-Authenticate`].get.challenges.head shouldEqual HttpChallenges.basic("secure")
      }
    }

    "be un-authorized when credentials are invalid when trying to update" in {
      val invalidCredentials = BasicHttpCredentials("some", "cracker")
      Post(s"/employee/$testEmployeeId") ~> addCredentials(invalidCredentials) ~> Route.seal(employeeRoutes) ~> check {
        status shouldEqual StatusCodes.Unauthorized
        header[`WWW-Authenticate`].get.challenges.head shouldEqual HttpChallenges.basic("secure")
      }
    }

    "be un-authorized when password is invalid when trying to update" in {
      val invalidPassword = BasicHttpCredentials(testCredential.username.get, "bad-password")
      Post(s"/employee/$testEmployeeId") ~> addCredentials(invalidPassword) ~> Route.seal(employeeRoutes) ~> check {
        status shouldEqual StatusCodes.Unauthorized
        header[`WWW-Authenticate`].get.challenges.head shouldEqual HttpChallenges.basic("secure")
      }
    }

    "delete employee should return no content" in {
      employeeServiceCall = (_) => { Future { DeleteEmployeeCompleted(testEmployeeId) } }

      Delete(s"/employee/$testEmployeeId") ~> addCredentials(validCredentials) ~> employeeRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.NoContent
        contentType shouldEqual `NoContentType`
      }
    }

    "be un-authorized when not authenticated when trying to delete" in {
      Delete(s"/employee/$testEmployeeId") ~> Route.seal(employeeRoutes) ~> check {
        status shouldEqual StatusCodes.Unauthorized
        header[`WWW-Authenticate`].get.challenges.head shouldEqual HttpChallenges.basic("secure")
      }
    }

    "be un-authorized when credentials are invalid when trying to delete" in {
      val invalidCredentials = BasicHttpCredentials("some", "cracker")
      Delete(s"/employee/$testEmployeeId") ~> addCredentials(invalidCredentials) ~> Route.seal(employeeRoutes) ~> check {
        status shouldEqual StatusCodes.Unauthorized
        header[`WWW-Authenticate`].get.challenges.head shouldEqual HttpChallenges.basic("secure")
      }
    }

    "be un-authorized when password is invalid when trying to delete" in {
      val invalidPassword = BasicHttpCredentials(testCredential.username.get, "bad-password")
      Delete(s"/employee/$testEmployeeId") ~> addCredentials(invalidPassword) ~> Route.seal(employeeRoutes) ~> check {
        status shouldEqual StatusCodes.Unauthorized
        header[`WWW-Authenticate`].get.challenges.head shouldEqual HttpChallenges.basic("secure")
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
