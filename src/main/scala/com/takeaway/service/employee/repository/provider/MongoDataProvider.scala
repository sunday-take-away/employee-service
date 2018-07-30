package com.takeaway.service.employee.repository.provider

import com.mongodb.MongoSocketOpenException
import com.takeaway.service.employee.repository.provider.exception.DataProviderException
import org.mongodb.scala.{ MongoClient, MongoCollection, MongoDatabase }

import scala.reflect.ClassTag

class MongoDataProvider(mongoClient: MongoClient, database: MongoDatabase) {
  private var collections = Map[String, MongoCollection[_]]()

  def collection[T](name: String)(implicit c: ClassTag[T]): MongoCollection[T] = {
    if (!collections.contains(name)) {
      val actualCollectionName = if (name.startsWith("raw-")) name.stripPrefix("raw-") else name
      collections = collections + (name -> database.getCollection[T](actualCollectionName))
    }
    collections(name).asInstanceOf[MongoCollection[T]]
  }

  def close() {
    if (mongoClient != null) mongoClient.close()
  }
}

object MongoDataProvider {
  def apply(settings: DataProviderSettings): MongoDataProvider = {
    try {
      println(s"database connection url:'${settings.url}' and database:'${settings.database}'")
      val mongoClient: MongoClient = MongoClient(settings.url)
      val database: MongoDatabase = mongoClient.getDatabase(settings.database)
      new MongoDataProvider(mongoClient, database)
    } catch {
      case iae: IllegalArgumentException => throw new DataProviderException(s"Unable to connect to mongo database, problem with url for config:'$settings'")
      case msoe: MongoSocketOpenException => throw new DataProviderException(s"Unable to connect to mongo database, database not available for config:'$settings'")
    }
  }
}
