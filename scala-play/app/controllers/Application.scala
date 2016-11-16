package controllers

import com.google.inject.Inject
import models.Item
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.database.ItemLogic

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(logic: ItemLogic) extends Controller {

  def getItems(detail: Boolean, start: String, size: Int) = Action.async {
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
}