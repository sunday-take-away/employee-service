package com.takeaway.service.employee.repository.data.codec.operation

import com.takeaway.service.employee.repository.data.extender.ObjectIdExtender._
import com.takeaway.service.employee.utility.StringUtility._
import org.bson.BsonWriter
import org.joda.time.{ DateTime, LocalDate }

trait CodecWriter {

  def writeObjectId(writer: BsonWriter, id: Option[String]) {
    if (id.isDefined) writer.writeObjectId("_id", id.to_object_id)
  }

  def writeOptionString(writer: BsonWriter, field: String, value: Option[String]) {
    if (value.has_value()) writer.writeString(field, value.get)
  }

  def writeOptionDateTime(writer: BsonWriter, field: String, value: Option[DateTime]) {
    if (value.isDefined) writer.writeDateTime(field, value.get.toDate.getTime)
  }

  def writeOptionLocalDate(writer: BsonWriter, field: String, value: Option[LocalDate]) {
    if (value.isDefined) writer.writeDateTime(field, value.get.toDate.getTime)
  }

  def writeStringArray(writer: BsonWriter, field: String, values: List[String]) {
    if (values.isEmpty) return
    writer.writeStartArray(field)
    values.foreach(v => writer.writeString(v))
    writer.writeEndArray()
  }
}
