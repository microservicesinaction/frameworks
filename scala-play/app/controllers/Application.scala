package controllers

import com.google.inject.Inject
import models.Item
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.database.ItemLogic

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(logic: ItemLogic) extends Controller {

  def getItems(detail: Option[Boolean], start: Option[String], size: Option[Int]) = Action.async {
    //todo use details and start parameters. Right now only filtering on size
    logic.getItems(detail, start, size).map(r =>
      Ok(Json.toJson(r))
    )
  }

  def getItemById(id: Int) = Action.async {
    logic.getItem(id).map(r =>
      Ok(Json.toJson(r))
    )
  }

  def updateById(id: Int) = Action.async { request =>
    logic.updateItem(id, request.body.asJson.get.as[Item]).map(r => //todo .get
      Ok(r)
    )
  }

  def registerItem = Action.async { request =>
    logic.registerItem(request.body.asJson.get.as[Item]).map(r => // todo doesn't make sense to send ID in request
      Ok(r)                                                       // todo .get
    )
  }

  def deleteItem(id: Int) = Action.async { request =>
    logic.deleteItem(id).map(r =>
      Ok(r)
    )
  }
}