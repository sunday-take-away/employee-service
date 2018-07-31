package com.takeaway.service.employee.repository.init

import com.takeaway.service.employee.repository.{ CredentialRepository, EmployeeRepository }
import com.typesafe.config.Config

object Repositories extends RepositoryInitializer {

  override def initialize(config: Config) {
    super.initialize(config)

    repositories = Map[String, AnyRef](
      "EmployeeRepository" -> new EmployeeRepository(dataProvider),
      "CredentialRepository" -> new CredentialRepository(dataProvider))
  }

}
