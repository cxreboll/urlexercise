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

/**
  * Produces a short version of an URL
  *
  * @param configuration
  * @param cache
  * @param db
  * @param executionContext
  */
class Shortener @Inject()(configuration: Configuration, cache: AsyncCacheApi, db: UrlCollection)
                         (implicit  executionContext: ExecutionContext) {
  // Available chars to produce a short url 26 + 26 + 10 = 62
  val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')
  // How long a short URL suffix should be
  val size = configuration.get[Int]("short.length")
  // How long should we keep it in cache, seconds, default is 1 hr
  val time = configuration.get[Int]("short.time")
  val duration = time seconds

  /**
    * Get a record based on short version of URL
    *
    * @param short
    * @return
    */
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

  /**
    * Produce a short version of an URL
    *
    * @param short
    * @param length
    * @return
    */
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

  /**
    * Generate a suffix, length default is 7, with 62 characters, that would give us:
    * 3.521614606𝐸+12 (or 3,521,614,606,208) possible permutations, enough to avoid a clash for this POC.
    *
    * @param length
    * @return
    */
  def generateRandom(length: Int): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      val randomNum = util.Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString
  }

}
