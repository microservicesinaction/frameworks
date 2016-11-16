package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Quantities(count: Int, state: String)
object     Quantities { implicit val quantitiesFormat = Json.format[Quantities] }

case class StockItems(itemId: Int, state: String)
object     StockItems { implicit val stockItemsFormat = Json.format[StockItems] }

case class Stock(quantities: Quantities, sku: Sku, stockItems: StockItems)
object     Stock {
  val stockReads: Reads[Stock] = (
      (JsPath \ "quantities").read[Quantities] and
      (JsPath \ "sku").read[String].map { value => Sku(value) } and
      (JsPath \ "stockItems").read[StockItems]
    )(Stock.apply _)

  val stockWrites: Writes[Stock] = (
      (JsPath \ "quantities").write[Quantities] and
      (JsPath \ "sku").write[String].contramap { (sku: Sku) => sku.value } and
      (JsPath \ "stockItems").write[StockItems]
    )(unlift(Stock.unapply))

  implicit val stockFormat: Format[Stock] =
    Format(stockReads, stockWrites)
}

case class StockRecord(id: Int, quantitiesCount: Int, quantitiesState: String, sku: String, stockItemId: Int, stockState: String) {
  def toStock: Stock = Stock(Quantities(quantitiesCount, quantitiesState), Sku(sku), StockItems(stockItemId, stockState))
}