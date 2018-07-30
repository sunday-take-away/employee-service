package com.takeaway.service.employee.support

import com.takeaway.service.employee.repository.provider.{ DataProviderSettingsLoader, MongoDataProvider }
import com.typesafe.config.ConfigFactory
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }

class DatabaseBaseSpec extends WordSpecLike with Matchers with BeforeAndAfterAll {
  protected var internalDataProvider: MongoDataProvider = _

  def dataProvider: MongoDataProvider = {
    if (internalDataProvider == null) {
      val config = ConfigFactory.load()
      val settings = DataProviderSettingsLoader(config).load()
      internalDataProvider = MongoDataProvider(settings)
    }
    internalDataProvider
  }

  override def afterAll {
    dataProvider.close()
  }
}
