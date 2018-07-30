package com.takeaway.service.employee.utility

import com.takeaway.service.employee.utility.DateTimeUtility._
import org.joda.time.DateTime
import org.scalatest.{ Matchers, WordSpecLike }

class DateTimeUtilitySpec extends WordSpecLike with Matchers {

  "A date time utility" must {

    "create a date from a string value" in {
      val date = "27.06.2018 10:33:12".to_date()
      assert(date.isInstanceOf[DateTime])
    }

    "not create date when value is null" in {
      val nullValue: String = null
      assert(nullValue.to_date() == null)
    }

    "create a string value from a date" in {
      val date = "27.06.2018 10:33:12".to_date()
      assert(date.to_value() == "27.06.2018 10:33:12")
    }

    "not create date value when date is null" in {
      val nullDate: DateTime = null
      assert(nullDate.to_value() == null)
    }

  }
}
