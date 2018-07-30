package com.takeaway.service.employee.repository.data.extender

import com.takeaway.service.employee.utility.StringUtility._
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Updates.{ set, unset }

object RepositoryUpdateExtender {

  implicit class OptionUpdateOperationExtender(value: Option[_]) {
    def update_field(fieldName: String): Bson = {
      value match {
        case None => unset(fieldName)
        case Some(heldValue) => heldValue match {
          case s: String => if (s.has_value()) set(fieldName, s) else unset(fieldName)
          case _ => value.fold(unset(fieldName))(v => set(fieldName, v))
        }
      }
    }
  }

  implicit class StringListUpdateOperationExtender(value: List[String]) {
    def update_field(fieldName: String): Bson = {
      if (value.isEmpty) unset(fieldName) else set(fieldName, value.head)
    }
  }
}
