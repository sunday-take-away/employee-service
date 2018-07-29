package com.takeaway.service.employee.boot

trait ServiceBootLifeCycle extends ServiceBoot {

  init()

  loadActorSystem(config)

  loadRepositories()

  loadAkkaServices(system, config)

  loadHttpServer(config, system, executor, materializer)
}
