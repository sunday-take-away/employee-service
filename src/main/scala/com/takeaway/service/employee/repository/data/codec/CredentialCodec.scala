package com.takeaway.service.employee.repository.data.codec

import com.takeaway.service.employee.model.security.Credential
import org.bson.codecs.{ Codec, DecoderContext, EncoderContext }
import org.bson.{ BsonReader, BsonType, BsonWriter }
import org.joda.time.DateTime

class CredentialCodec extends Codec[Credential] with CodecBase {

  override def encode(writer: BsonWriter, credential: Credential, encoderContext: EncoderContext): Unit = {
    writer.writeStartDocument()
    writeObjectId(writer, credential.id)
    writeOptionString(writer, "username", credential.username)
    writeOptionString(writer, "password", credential.password)
    writeOptionString(writer, "accessGroup", credential.group)
    writeOptionDateTime(writer, "created", credential.created)
    writer.writeEndDocument()
  }

  override def getEncoderClass: Class[Credential] = classOf[Credential]

  override def decode(reader: BsonReader, decoderContext: DecoderContext): Credential = {
    var id: Option[String] = None
    var username: Option[String] = None
    var password: Option[String] = None
    var group: Option[String] = None
    var created: Option[DateTime] = None

    reader.readStartDocument()

    while (reader.readBsonType ne BsonType.END_OF_DOCUMENT) {
      val fieldName = reader.readName

      fieldName match {
        case "_id" => id = readOptionObjectId(reader)
        case "username" => username = readOptionString(reader)
        case "password" => password = readOptionString(reader)
        case "accessGroup" => group = readOptionString(reader)
        case "created" => created = readOptionDateTime(reader)
      }
    }

    reader.readEndDocument()

    Credential(id, username, password, group, created)
  }

}
