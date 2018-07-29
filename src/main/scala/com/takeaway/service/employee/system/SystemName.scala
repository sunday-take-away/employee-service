package com.takeaway.service.employee.system

trait SystemName {
  def systemName: String = {
    SystemName.systemName
  }

  def setSystemName(serviceName: String) {
    SystemName.systemName = serviceName
  }
}

object SystemName {
  var systemName: String = _
}