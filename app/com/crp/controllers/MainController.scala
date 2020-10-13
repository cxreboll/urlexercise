package com.crp.controllers

import com.crp.mongo.UrlCollection
import com.crp.process.Shortener
import com.crp.util.Entities._
import com.google.inject.Inject
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.{ExecutionContext, Future}

class MainController @Inject() (wsClient: WSClient, cc: ControllerComponents, db: Shortener)
                               (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {

  def index()= Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def version() = Action { implicit request: Request[AnyContent] =>

    Ok(Json.obj("version" -> "res", "project" -> "projectname"))
  }

  def short(short: String) = Action.async {  request =>
    for {
      result <- db.getLongUrl(short)
    } yield  {
      if (result.isDefined) {
        println(result.get.url)
        Redirect(result.get.url)
      } else {
        NotFound("URL not found")
      }
    }
  }

  def push() = Action.async {  request =>
    for {
      payload <- Future.successful(request.body.asJson.map(obj => obj.as[ShortUrl]))
      _ = println(request.body.asText)
      result <- payload.map(t => {db.shortUrl(t)}).getOrElse(Future.failed(new Throwable("Unable to insert")))
    } yield Ok(Json.toJson(result))
  }
}