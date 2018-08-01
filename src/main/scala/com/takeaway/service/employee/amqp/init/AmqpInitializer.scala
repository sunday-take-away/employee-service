package com.takeaway.service.employee.amqp.init

import com.takeaway.service.employee.amqp.provider.{ AmqpProvider, AmqpProviderSettingsLoader }
import com.typesafe.config.Config

trait AmqpInitializer {
  protected var amqpProvider: AmqpProvider = _

  def initialize(config: Config) {
    val settings = AmqpProviderSettingsLoader(config).load()
    amqpProvider = AmqpProvider(settings)
  }

  def getQueueProvider: AmqpProvider = {
    println("getting queue provider!")
    amqpProvider
  }
}
