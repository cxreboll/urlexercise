package com.crp.process

import com.crp.UnitSpec
import com.crp.mongo.UrlCollection
import com.crp.util.Entities.ShortUrl
import org.mockito.Mock
import play.api.{ConfigLoader, Configuration}
import play.api.cache.{AsyncCacheApi, SyncCacheApi}
import play.api.libs.json.Json
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers.{any, _}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.reflect.ClassTag

class ShortenerSpec extends UnitSpec {
   implicit val ex = scala.concurrent.ExecutionContext.global
  "Shortener object " should {
    "bring unique objects without cache" in {
      import play.api.ConfigLoader

      val configMock = mock(classOf[Configuration])
      val cacheApi = mock(classOf[AsyncCacheApi])
      val configLoader = mock(classOf[ConfigLoader[Int]])
      val urlCollection = mock(classOf[UrlCollection])
      when(configMock.get[Int]("short.length")(configLoader)).thenReturn(7)
      when(cacheApi.get(anyString())(any[ClassTag[ShortUrl]])).thenReturn(Future.successful(
        Some(ShortUrl(Some("id"),  "https://google.com", Some("id"), Some(1)))))
      val shortener = new Shortener(configMock, cacheApi, urlCollection)


      val result = Await.result(shortener.shortUrl(ShortUrl(None, "https://google.com", None, None), Some(7)), 10 seconds)

      result.short.isDefined  mustBe(true)
    }
  }
}
