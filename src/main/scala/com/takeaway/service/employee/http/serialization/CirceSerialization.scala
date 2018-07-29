package com.takeaway.service.employee.http.serialization

import akka.http.scaladsl.marshalling.{ Marshaller, ToEntityMarshaller }
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.unmarshalling.{ FromEntityUnmarshaller, Unmarshaller }
import akka.util.ByteString
import io.circe._

trait CirceSerialization {

  private val jsonStringUnMarshaller = Unmarshaller.byteStringUnmarshaller.forContentTypes(`application/json`).mapWithCharset {
    case (ByteString.empty, _) => throw Unmarshaller.NoContentException
    case (data, charset) => data.decodeString(charset.nioCharset.name)
  }

  private val jsonStringMarshaller = Marshaller.stringMarshaller(`application/json`)

  implicit def circeUnMarshaller[A](implicit decoder: Decoder[A]): FromEntityUnmarshaller[A] =
    jsonStringUnMarshaller.map(jawn.decode(_).fold(throw _, identity))

  implicit def circeToEntityMarshaller[A](implicit encoder: Encoder[A], printer: Json => String = Printer.noSpaces.pretty): ToEntityMarshaller[A] =
    jsonStringMarshaller.compose(printer).compose(encoder.apply)
}
