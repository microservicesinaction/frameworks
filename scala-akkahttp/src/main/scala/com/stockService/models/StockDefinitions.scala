package com.stockService.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Quantities(count: Int, state: String)

case class Stock(quantities: Quantities, sku: Sku)

object StockJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val quantitiesFormat = jsonFormat2(Quantities)
  implicit val skuFormat = jsonFormat1(Sku)

  implicit val stockFormat = jsonFormat2(Stock)
}

case class StockRecord(id: Int, quantitiesCount: Int, quantitiesState: String, sku: String, stockItemId: Int) {
  def toStock: Stock = Stock(Quantities(quantitiesCount, quantitiesState), Sku(sku))
}
