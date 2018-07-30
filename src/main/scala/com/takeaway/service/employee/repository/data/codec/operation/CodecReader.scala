package com.takeaway.service.employee.repository.data.codec.operation

import org.bson.{ BsonReader, BsonType }
import org.joda.time.{ DateTime, LocalDate }

trait CodecReader {

  def readOptionObjectId(reader: BsonReader): Option[String] = {
    Some(reader.readObjectId().toHexString)
  }

  def readOptionString(reader: BsonReader): Option[String] = {
    Some(reader.readString())
  }

  def readOptionDateTime(reader: BsonReader): Option[DateTime] = {
    Some(new DateTime(reader.readDateTime()))
  }

  def readOptionLocalDate(reader: BsonReader): Option[LocalDate] = {
    Some(new LocalDate(reader.readDateTime()))
  }

  def readStringArray(reader: BsonReader): List[String] = {
    reader.readStartArray()
    var list = List[String]()
    while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
      val value = reader.readString()
      list = value :: list
    }
    reader.readEndArray()
    list
  }
}
