package com.crp.process

//import com.crp.util.Entities.{Attendee, Invites, Partner}
import java.util.UUID

import com.crp.mongo.UrlCollection
import com.crp.util.Entities.ShortUrl
import com.google.inject.Inject
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.Configuration
import play.api.cache.AsyncCacheApi

import scala.concurrent._
import scala.concurrent.duration._

class Shortener @Inject()(configuration: Configuration, cache: AsyncCacheApi, db: UrlCollection)
                         (implicit  executionContext: ExecutionContext) {
  val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')
  val size = configuration.get[Int]("short.length")
  val time = configuration.get[Int]("short.time")
  val duration = time seconds

  def getLongUrl(short: String) = {
    for {
      obj <- cache.get[ShortUrl](short)
      dbobj <- obj match {
        case None => db.getShort(short)
        case _ => Future.successful(obj)
      }
      _ = dbobj.foreach(cache.set(short, _, duration))
    } yield dbobj
  }

  def shortUrl(short: ShortUrl, length: Option[Int] = None) = {
    val entry = if (!short.url.startsWith("https://") && !short.url.startsWith("http://")) {
      short.copy(url = "http://"+ short.url)
    } else {
      short
    }
    for {
      obj <- cache.get[ShortUrl](entry.url)
      dbobj <- obj match {
        case None => db.get(entry.url)
        case _ => Future.successful(obj)
      }
      nobj <- dbobj match {
        case None => {
          val s = length.getOrElse(size)
          val uuid = UUID.randomUUID()
          Future.successful(entry.copy(Some(uuid.toString), short = Some(generateRandom(s)), created = Some(System.currentTimeMillis())))
        }
        case _ => Future.successful(dbobj.get)
      }
      _ = db.insert(nobj)
      _ = cache.set(entry.url, nobj, duration)
    } yield nobj
  }

  def generateRandom(length: Int): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      val randomNum = util.Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString
  }

}
