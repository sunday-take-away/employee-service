package com.takeaway.service.employee.boot

trait ServiceBootLifeCycle extends ServiceBoot {

  init()

  loadActorSystem(config)

  loadRepositories(config)

  loadAkkaServices(system, config)

  loadHttpServer(config, system, executor, materializer)
}
