package com.takeaway.service.employee.repository.provider

import com.typesafe.config.Config

case class DataProviderSettings(url: String, database: String)

class DataProviderSettingsLoader(config: Config) {

  def load(): DataProviderSettings = {
    val url = config.getString("data.connection.url")
    val database = config.getString("data.connection.db")
    DataProviderSettings(url, database)
  }
}

object DataProviderSettingsLoader {
  def apply(config: Config): DataProviderSettingsLoader = {
    new DataProviderSettingsLoader(config)
  }
}