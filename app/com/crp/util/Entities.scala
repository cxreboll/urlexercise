package com.crp.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json._
import reactivemongo.bson.Macros


object Entities {

  case class ShortUrl(id: Option[String], url: String, short: Option[String], created: Option[Long])

  implicit val ownerAndBrandHandler = Macros.handler[ShortUrl]

  case class ATestEntity(field: String, fieldb: Boolean, fieldc: List[Int])

  implicit val formatInvites = Json.format[ShortUrl]
}