package com.takeaway.service.employee.http.route

import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }

class EchoRouteSpec extends WordSpecLike with ScalatestRouteTest with Matchers with BeforeAndAfterAll with EchoRoute {

  "A service http echo end-point" must {

    "return simple echo message with date" in {
      Get("/echo") ~> echoRoutes ~> check {
        status.isSuccess() shouldBe true
        status shouldEqual StatusCodes.OK
        contentType shouldEqual `application/json`

        val echoResponse = responseAs[ByteString].decodeString("UTF-8")

        echoResponse should include("serviceName")
        echoResponse should include("echo")
      }
    }

  }

}
