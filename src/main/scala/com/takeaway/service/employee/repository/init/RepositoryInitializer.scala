package com.takeaway.service.employee.repository.init

import scala.reflect.ClassTag

trait RepositoryInitializer {
  protected var repositories: Map[String, _] = _

  def getRepository[T: ClassTag]()(implicit m: Manifest[T]) = {
    val repositoryName = m.runtimeClass.getSimpleName
    repositories(repositoryName).asInstanceOf[T]
  }
}
