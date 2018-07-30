package com.takeaway.service.employee.repository.data.extender

import org.bson.types.ObjectId

object ObjectIdExtender {

  implicit class ObjectIdExtender(id: ObjectId) {
    def to_string: String = {
      if (id == null) null else id.toString
    }
  }

  implicit class StringObjectIdExtender(id: String) {
    def to_object_id: ObjectId = {
      if (id == null) null else new ObjectId(id)
    }
  }

  implicit class OptionalStringObjectIdExtender(id: Option[String]) {
    def to_object_id: ObjectId = {
      if (id.isEmpty) null else new ObjectId(id.get)
    }
  }
}
