package com.takeaway.service.employee.http.serialization

import com.takeaway.service.employee.utility.LocalDateUtility._
import io.circe.Decoder.Result
import io.circe.{ Decoder, Encoder, HCursor, Json }
import org.joda.time.LocalDate

trait LocalDateSerialization {

  implicit val localDateSerialization: Encoder[LocalDate] with Decoder[LocalDate] = new Encoder[LocalDate] with Decoder[LocalDate] {
    override def apply(a: LocalDate): Json = {
      Encoder.encodeString.apply(a.to_value())
    }

    override def apply(c: HCursor): Result[LocalDate] = {
      Decoder.decodeString.map(s => s.to_date()).apply(c)
    }
  }

}
