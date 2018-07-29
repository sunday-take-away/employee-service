package com.takeaway.service.employee.http.serialization

import com.takeaway.service.employee.utility.DateTimeUtility._
import io.circe.Decoder.Result
import io.circe.{ Decoder, Encoder, HCursor, Json }
import org.joda.time.DateTime

trait DateTimeSerialization {

  implicit val dateTimeSerialization: Encoder[DateTime] with Decoder[DateTime] = new Encoder[DateTime] with Decoder[DateTime] {
    override def apply(a: DateTime): Json = {
      Encoder.encodeString.apply(a.to_value())
    }

    override def apply(c: HCursor): Result[DateTime] = {
      Decoder.decodeString.map(s => s.to_date()).apply(c)
    }
  }

}
