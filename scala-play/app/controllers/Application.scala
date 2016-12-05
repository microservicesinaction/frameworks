package controllers

import com.google.inject.Inject
import models.{Item, Stock}
import play.api.libs.json.Json
import play.api.mvc._
import services.StockLogic
import services.database.ItemLogic

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Application @Inject()(itemLogic: ItemLogic, stockLogic: StockLogic) extends Controller {
  val requestErrorMessage = "See Documentation about correct request payload"

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
    request.body.asJson.fold(Future.successful(BadRequest(requestErrorMessage))) { payload =>
      itemLogic.updateItem(id, payload.as[Item]).map(r =>
        Ok(r)
      )
    }
  }

  def registerItem = Action.async { request =>
    request.body.asJson.fold(Future.successful(BadRequest(requestErrorMessage))) { payload =>
      itemLogic.registerItem(payload.as[Item]).map(r =>
        Ok(r)
      )
    }
  }

  def deleteItem(id: Int) = Action.async {
    itemLogic.deleteItem(id).map(r =>
      Ok(r)
    )
  }

  def addStockByItemId(itemId: Int) = Action.async { request =>
    request.body.asJson.fold(Future.successful(BadRequest(requestErrorMessage))) { payload =>
      stockLogic.addStock(itemId, payload.as[Stock]).map(r =>
        Ok(r)
      )
    }
  }
}

class BadIncomingRequest extends Exception