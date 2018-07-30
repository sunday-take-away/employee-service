package com.takeaway.service.employee.repository.data.codec

import com.takeaway.service.employee.repository.data.codec.operation.{ CodecReader, CodecWriter }

trait CodecBase extends CodecWriter with CodecReader