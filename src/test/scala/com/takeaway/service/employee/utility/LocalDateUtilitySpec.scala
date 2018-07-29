package com.takeaway.service.employee.utility

import com.takeaway.service.employee.utility.LocalDateUtility._
import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpecLike}

class LocalDateUtilitySpec extends WordSpecLike with Matchers {

  "A local date utility" must {

    "create a date from a string value" in {
      val date = "2018-07-30".to_date()
      assert(date.isInstanceOf[LocalDate])
    }

    "not create date when value is null" in {
      val nullValue: String = null
      assert(nullValue.to_date() == null)
    }

    "create a string value from a date" in {
      val date = "2018-07-30".to_date()
      assert(date.to_value() == "2018-07-30")
    }

    "not create date value when date is null" in {
      val nullDate: LocalDate = null
      assert(nullDate.to_value() == null)
    }

  }
}
