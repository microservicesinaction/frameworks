package controllers

import com.google.inject.Inject
import models.{Item, Stock}
import play.api.libs.json.Json
import play.api.mvc._
import services.StockLogic
import services.database.ItemLogic

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(itemLogic: ItemLogic, stockLogic: StockLogic) extends Controller {

  def getItems(detail: Option[Boolean], start: Option[String], size: Option[Int]) = Action.async {
    //todo use details and start parameters. Right now only filtering on size
    itemLogic.getItems(detail, start, size).map(r =>
      Ok(Json.toJson(r))
    )
  }

  def getItemById(id: Int) = Action.async {
    itemLogic.getItem(id).map(r =>
      Ok(Json.toJson(r))
    )
  }

  def updateById(id: Int) = Action.async { request =>
    itemLogic.updateItem(id, request.body.asJson.get.as[Item]).map(r => //todo .get
      Ok(r)
    )
  }

  def registerItem = Action.async { request =>
    itemLogic.registerItem(request.body.asJson.get.as[Item]).map(r => // todo doesn't make sense to send ID in request
      Ok(r)                                                       // todo .get
    )
  }

  def deleteItem(id: Int) = Action.async { request =>
    itemLogic.deleteItem(id).map(r =>
      Ok(r)
    )
  }

  def addStockByItemId(itemId: Int) = Action.async { request =>
    stockLogic.addStock(itemId, request.body.asJson.get.as[Stock]).map(r =>
      Ok(r)
    )
  }
}