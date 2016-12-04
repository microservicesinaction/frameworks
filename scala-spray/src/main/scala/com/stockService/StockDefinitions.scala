package com.stockService

import spray.json.DefaultJsonProtocol


case class Quantities(count: Int, state: String)

case class StockItems(itemId: Int, state: String)

case class Stock(quantities: Quantities, sku: Sku, stockItems: StockItems)
object StockJsonProtocol extends DefaultJsonProtocol {
  implicit val quantities = jsonFormat2(Quantities)
  implicit val skuFormat = jsonFormat1(Sku)
  implicit val stockItemsFormat = jsonFormat2(StockItems)

  implicit val stockFormat = jsonFormat3(Stock)
}

case class StockRecord(id: Int, quantitiesCount: Int, quantitiesState: String, sku: String, stockItemId: Int, stockState: String) {
  def toStock: Stock = Stock(Quantities(quantitiesCount, quantitiesState), Sku(sku), StockItems(stockItemId, stockState))
}