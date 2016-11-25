package models

case class Quantities(count: Int, state: String)
case class StockItems(itemId: Int, state: String)
case class Stock(quantities: Quantities, sku: Sku, stockItems: StockItems)