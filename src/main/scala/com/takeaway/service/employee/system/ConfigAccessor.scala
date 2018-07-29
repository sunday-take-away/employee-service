package com.takeaway.service.employee.system

import com.typesafe.config.Config

trait ConfigAccessor {
  implicit val config: Config
}
