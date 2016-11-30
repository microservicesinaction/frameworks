package com.stockService


case class Quantities(count: Int, state: String)

case class StockItems(itemId: Int, state: String)

case class Stock(quantities: Quantities, sku: Sku, stockItems: StockItems)

case class StockRecord(id: Int, quantitiesCount: Int, quantitiesState: String, sku: String, stockItemId: Int, stockState: String) {
  def toStock: Stock = Stock(Quantities(quantitiesCount, quantitiesState), Sku(sku), StockItems(stockItemId, stockState))
}