package com.takeaway.service.employee.amqp.provider

import akka.stream.alpakka.amqp.scaladsl.AmqpSink
import akka.stream.alpakka.amqp.{ AmqpCredentials, AmqpDetailsConnectionProvider, AmqpSinkSettings, QueueDeclaration }

class AmqpProvider(connectionProvider: AmqpDetailsConnectionProvider, queueDeclaration: QueueDeclaration) {

  def sink() = {
    AmqpSink.simple(
      AmqpSinkSettings(connectionProvider)
        .withRoutingKey(queueDeclaration.name)
        .withDeclarations(queueDeclaration))
  }

  def queueName = queueDeclaration.name

  def close() {
    if (connectionProvider != null) connectionProvider.get.close()
  }
}

object AmqpProvider {
  def apply(settings: AmqpProviderSettings): AmqpProvider = {
    val connectionProvider = AmqpDetailsConnectionProvider(settings.interface, settings.port).withCredentials(AmqpCredentials(settings.username, settings.password))
    val queue = QueueDeclaration(settings.queueName).withDurable(settings.queueDurable)
    new AmqpProvider(connectionProvider, queue)
  }
}

