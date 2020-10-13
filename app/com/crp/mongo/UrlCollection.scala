package com.crp.mongo

import com.crp.util.Entities.ShortUrl
import com.google.inject.{Inject, Singleton}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.indexes.{Index, IndexType}
import reactivemongo.bson.BSONDocument

import scala.concurrent.{ExecutionContext, Future}

/**
  * DAO object to urls collection
  *
  * @param connection
  * @param executionContext
  */
@Singleton
class UrlCollection @Inject()(connection: Connection)(implicit executionContext: ExecutionContext) {
  lazy val mongocollection : Future[BSONCollection] = connection.getCollection("urls")

  createIndexes()

  /**
    * Create table indexes.
    *
    * @return
    */
  def createIndexes() = {
    for {
      collection <- mongocollection
      _ <- collection.indexesManager.create(Index(Seq(("url", IndexType.Ascending))))
      _ <- collection.indexesManager.create(Index(Seq(("short", IndexType.Ascending))))
    } yield ()
  }

  /**
    * Insert an object in urls collection
    *
    * @param shortUrl
    */
  def insert(shortUrl: ShortUrl): Unit = {
    for {
      collection <- mongocollection
      writeResult <- collection.insert(false).one(shortUrl)
    } yield {
      if (!writeResult.ok) throw new Exception(writeResult.writeErrors.map(_.errmsg).mkString)
    }
  }

  /**
    * Get a document based on long URL
    *
    * @param url
    * @return
    */
  def get(url: String): Future[Option[ShortUrl]] = {
    val query = BSONDocument("url" -> url)
    for {
      collection <- mongocollection
      r <- collection.find(query).one[ShortUrl]
    } yield r
  }

  /**
    * Get a document based on short URL
    *
    * @param url
    * @return
    */
  def getShort(url: String): Future[Option[ShortUrl]] = {
    val query = BSONDocument("short" -> url)
    for {
      collection <- mongocollection
      r <- collection.find(query).one[ShortUrl]
    } yield r
  }
}
