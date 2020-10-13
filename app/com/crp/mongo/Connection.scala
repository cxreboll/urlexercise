package com.crp.mongo

import com.google.inject.{Inject, Singleton}
import play.api.Configuration
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

import reactivemongo.api.{MongoConnection}

/**
  * Generic connection object to mongo DB
  *
  * @param config
  * @param executionContext
  */
@Singleton
class Connection @Inject() (config: Configuration)(implicit executionContext: ExecutionContext) {

  lazy val driver = new reactivemongo.api.MongoDriver
  lazy val uri = config.get[String]("mongo.url")

  lazy val parsedURI = Future.fromTry(MongoConnection.parseURI(uri))

  lazy val connection = for {
    uri <- parsedURI
    con <- Future.fromTry(driver.connection(uri, false))
  } yield con

  def getCollection(name: String): Future[BSONCollection] = {
    for {
      uri <- parsedURI
      con <- connection
      dn <- Future.successful { uri.db.get }
      db <- con.database(dn)
    } yield db.collection(name)
  }
}

object Connection {

}
