package com.takeaway.service.employee.service.amqp

import com.takeaway.service.employee.amqp.init.AdvancedMessagingQueues
import com.takeaway.service.employee.support.{ ActorSpec, EmployeeTestSupport }
import com.takeaway.service.employee.system.akka.extender.ActorSystemExtender._
import org.scalatest.tagobjects.Slow

import scala.concurrent.duration._

class EmployeeMessageQueueActorSpec extends ActorSpec with EmployeeTestSupport {

  override def beforeAll(): Unit = {
    loadConfig()
    AdvancedMessagingQueues.initialize(config)
    system.actorOf(EmployeeMessageQueueActor.props(), EmployeeMessageQueueActor.name)
  }

  "A employee message queue actor" must {

    "send employee created message" taggedAs Slow in {
      val actor = system.lookup_existing_actor(EmployeeMessageQueueActor.name)

      actor ! EmployeeCreated(testNoneCreatedEmployeeId)

      expectNoMessage(5.seconds)
    }

  }
}