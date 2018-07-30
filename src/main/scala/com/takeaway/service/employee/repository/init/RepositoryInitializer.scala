package com.takeaway.service.employee.repository.init

import com.takeaway.service.employee.repository.provider.{ DataProviderSettingsLoader, MongoDataProvider }
import com.typesafe.config.Config

import scala.reflect.ClassTag

trait RepositoryInitializer {
  protected var dataProvider: MongoDataProvider = _
  protected var repositories: Map[String, _] = _

  def initialize(config: Config) {
    val settings = DataProviderSettingsLoader(config).load()
    dataProvider = MongoDataProvider(settings)
  }

  def getDataProvider: MongoDataProvider = dataProvider

  def getRepository[T: ClassTag]()(implicit m: Manifest[T]) = {
    val repositoryName = m.runtimeClass.getSimpleName
    repositories(repositoryName).asInstanceOf[T]
  }
}
