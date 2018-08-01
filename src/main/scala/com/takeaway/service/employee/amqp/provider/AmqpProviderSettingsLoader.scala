package com.takeaway.service.employee.amqp.provider

import com.typesafe.config.Config

case class AmqpProviderSettings(interface: String, port: Int, username: String, password: String, queueName: String, queueDurable: Boolean)

class AmqpProviderSettingsLoader(config: Config) {

  def load(): AmqpProviderSettings = {
    val interface = config.getString("amqp.connection.interface")
    val port = config.getInt("amqp.connection.port")
    val username = config.getString("amqp.connection.username")
    val password = config.getString("amqp.connection.password")
    val queueName = config.getString("amqp.queue.name")
    val queueDurable = config.getBoolean("amqp.queue.durable")
    AmqpProviderSettings(interface, port, username, password, queueName, queueDurable)
  }
}

object AmqpProviderSettingsLoader {
  def apply(config: Config): AmqpProviderSettingsLoader = {
    new AmqpProviderSettingsLoader(config)
  }
}
