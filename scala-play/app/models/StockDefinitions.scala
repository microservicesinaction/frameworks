package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Quantities(count: Int, state: String)

object Quantities {
  implicit val quantitiesFormat = Json.format[Quantities]
}

case class Stock(quantities: Quantities, sku: Sku)

object Stock {
  val stockReads: Reads[Stock] = (
    (JsPath \ "quantities").read[Quantities] and
      // Negates the need to write "value" in the json payload
      (JsPath \ "sku").read[String].map { value => Sku(value) }
    ) (Stock.apply _)

  val stockWrites: Writes[Stock] = (
    (JsPath \ "quantities").write[Quantities] and
      (JsPath \ "sku").write[String].contramap { (sku: Sku) => sku.value }
    ) (unlift(Stock.unapply))

  implicit val stockFormat: Format[Stock] = Format(stockReads, stockWrites)
}

case class StockRecord(id: Int, quantitiesCount: Int, quantitiesState: String, sku: String, stockItemId: Int) {
  def toStock: Stock = Stock(Quantities(quantitiesCount, quantitiesState), Sku(sku))
}