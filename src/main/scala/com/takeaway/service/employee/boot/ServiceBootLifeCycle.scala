package com.takeaway.service.employee.boot

trait ServiceBootLifeCycle extends ServiceBoot {

  init()

  loadActorSystem(config)

  loadRepositories(config)

  loadAdvancedMessagingQueues(config)

  loadAkkaServices(system, config)

  loadHttpServer(config, system, executor, materializer)
}
