package com.takeaway.service.employee.repository.init

object Repositories extends RepositoryInitializer {

  def initialize(): Unit = {
    repositories = Map[String, AnyRef]()
  }

}
