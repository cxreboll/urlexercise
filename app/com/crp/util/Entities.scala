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

//  val dateFormat = "yyyy-MM-dd"
//
//  implicit val jodaDateReads = Reads[DateTime](js =>
//    js.validate[String].map[DateTime](dtString =>
//      DateTime.parse(dtString, DateTimeFormat.forPattern(dateFormat))
//    )
//  )
//
//  implicit val jodaDateWrites: Writes[DateTime] = new Writes[DateTime] {
//    def writes(d: DateTime): JsValue = JsString(d.toString())
//  }
//
//  case class Partner(firstName: String, lastName: String, email: String, country: String,
//                     availableDates: List[DateTime])
//
//
//
//  case class Partners(partners: List[Partner])
//
//  implicit val formatATestEntity = Json.format[ATestEntity]
//  implicit val formatPartner = Json.format[Partner]
//
//  implicit val listPartnerReads = Reads[List[Partner]](js =>
//    js match {
//      case a:JsArray => JsSuccess(a.value.map(v => v.as[Partner]).toList)
//      case _ => JsError("Not able to parse input")
//    }
//  )
//
//  implicit val listPartnerWrites: Writes[List[Partner]] = new Writes[List[Partner]] {
//    def writes(d: List[Partner]): JsValue = {
//      val rs = d.map(tt => {
//        Json.toJson(tt)
//      })
//      JsArray(rs)
//    }
//  }
//
//  implicit val formatPartners = Json.format[Partners]
//
//  case class Attendee(attendeeCount: Int, attendees: List[String], name: String, startDate: Option[String] = None)
//  implicit val formatAttendee = Json.format[Attendee]
//
//  implicit val listAttendeeReads = Reads[List[Attendee]](js =>
//    js match {
//      case a:JsArray => JsSuccess(a.value.map(v => v.as[Attendee]).toList)
//      case _ => JsError("Not able to parse input")
//    }
//  )
//
//  implicit val listAttendeeWrites: Writes[List[Attendee]] = new Writes[List[Attendee]] {
//    def writes(d: List[Attendee]): JsValue = {
//      val rs = d.map(tt => {
//        Json.toJson(tt)
//      })
//      JsArray(rs)
//    }
//  }
//  case class Invites(countries: List[Attendee])
//
//  implicit val formatInvites = Json.format[Invites]
}