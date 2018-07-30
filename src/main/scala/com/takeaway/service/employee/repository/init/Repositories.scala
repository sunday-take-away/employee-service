package com.takeaway.service.employee.repository.init

import com.takeaway.service.employee.repository.EmployeeRepository
import com.typesafe.config.Config

object Repositories extends RepositoryInitializer {

  override def initialize(config: Config) {
    super.initialize(config)

    repositories = Map[String, AnyRef](
      "EmployeeRepository" -> new EmployeeRepository(dataProvider))
  }

}
