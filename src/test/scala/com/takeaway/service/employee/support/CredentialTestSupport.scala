package com.takeaway.service.employee.support

import com.takeaway.service.employee.model.security.Credential
import com.takeaway.service.employee.utility.DateTimeUtility._

trait CredentialTestSupport {

  val testCredential = Credential(Some("0b8cc021-6447-4cbe-b8fd-71230e297ea8"), Some("admin"), Some("password"), Some("Administrators"), Some("27.06.2018 10:33:12".to_date()))

}
