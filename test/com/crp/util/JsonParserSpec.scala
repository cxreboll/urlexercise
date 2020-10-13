package com.crp.util

import com.crp.UnitSpec
import com.crp.process.Shortener
import com.crp.util.Entities._
import org.joda.time.DateTime
import play.api.libs.json.Json

class JsonParserSpec extends UnitSpec {

  "ShortUrl object " should {
    "unmarshall to json properly" in {
      val anobject = ShortUrl(Some("id"), "https://google.com", Some("aaaa"), Some(System.currentTimeMillis()))
      val obj = Json.toJson(anobject)
      println(Json.stringify(obj))
      (obj \ "id").isDefined  mustBe(true)
    }
  }

  "ShortUrl json " should {
    "marshall to obj properly" in {
      val fullObj = Json.parse(payload).as[ShortUrl]
      val emptyObj = Json.parse(payloadEmpty).as[ShortUrl]

      fullObj.id.isDefined mustBe(true)
      emptyObj.id.isDefined mustBe(false)
    }
  }

  val payload =
    """
      |{
      | "id" : "uuid",
      | "url" : "https://google.com",
      | "short" : "aaaa",
      | "created" : 1602468116560
      |}
      |""".stripMargin

  val payloadEmpty =
    """
      |{
      | "url" : "https://google.com"
      |}
      |""".stripMargin

}