package com.takeaway.service.employee.repository

import com.takeaway.service.employee.repository.data.extender.ObjectIdExtender._
import org.bson.types.ObjectId

trait Repository {

  def generateNewObjectId(): ObjectId = ObjectId.get

  def getDefaultId(): Option[String] = Some(generateNewObjectId().to_string)

}
