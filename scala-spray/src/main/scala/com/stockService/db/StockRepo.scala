package com.stockService.db

import com.stockService.StockRecord
import slick.driver.H2Driver.api._
import slick.lifted.Tag


class StockRepo {

  val db = Database.forConfig("h2mem1")

  val stock = TableQuery[StockTable]

  class StockTable(tag: Tag) extends Table[StockRecord](tag, "STOCK") {

    def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    def quantitiesCount = column[Int]("QUANTITIES_COUNT")
    def quantitiesState = column[String]("QUANTITIES_STATE")
    def sku = column[String]("SKU")
    def stockItemId = column[Int]("STOCK_ITEM_ID")
    def stockState = column[String]("STOCK_STATE")

    def * = (id, quantitiesCount, quantitiesState, sku, stockItemId, stockState) <> ((StockRecord.apply _).tupled, StockRecord.unapply)
  }

}
