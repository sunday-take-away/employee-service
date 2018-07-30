package com.takeaway.service.employee.repository.data.codec

import com.takeaway.service.employee.model.Employee
import org.bson.codecs.{ Codec, DecoderContext, EncoderContext }
import org.bson.{ BsonReader, BsonType, BsonWriter }
import org.joda.time.{ DateTime, LocalDate }

class EmployeeCodec extends Codec[Employee] with CodecBase {

  override def encode(writer: BsonWriter, employee: Employee, encoderContext: EncoderContext): Unit = {
    writer.writeStartDocument()
    writeObjectId(writer, employee.id)
    writeOptionString(writer, "email", employee.email)
    writeOptionString(writer, "firstName", employee.firstName)
    writeOptionString(writer, "lastName", employee.lastName)
    writeOptionLocalDate(writer, "birthDay", employee.birthDay)
    writeStringArray(writer, "hobbies", employee.hobbies)
    writeOptionDateTime(writer, "created", employee.created)
    writer.writeEndDocument()
  }

  override def getEncoderClass: Class[Employee] = classOf[Employee]

  override def decode(reader: BsonReader, decoderContext: DecoderContext): Employee = {
    var id: Option[String] = None
    var email: Option[String] = None
    var firstName: Option[String] = None
    var lastName: Option[String] = None
    var birthDay: Option[LocalDate] = None
    var hobbies: List[String] = List[String]()
    var created: Option[DateTime] = None

    reader.readStartDocument()

    while (reader.readBsonType ne BsonType.END_OF_DOCUMENT) {
      val fieldName = reader.readName

      fieldName match {
        case "_id" => id = readOptionObjectId(reader)
        case "email" => email = readOptionString(reader)
        case "firstName" => firstName = readOptionString(reader)
        case "lastName" => lastName = readOptionString(reader)
        case "birthDay" => birthDay = readOptionLocalDate(reader)
        case "hobbies" => hobbies = readStringArray(reader)
        case "created" => created = readOptionDateTime(reader)
      }
    }

    reader.readEndDocument()

    Employee(id, email, firstName, lastName, birthDay, hobbies, created)
  }
}
