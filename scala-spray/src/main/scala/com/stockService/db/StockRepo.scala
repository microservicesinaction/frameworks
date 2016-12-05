package com.stockService.db

import com.stockService.models.StockRecord
import slick.driver.H2Driver.api._
import slick.lifted.Tag

class StockRepo {

  val db = Database.forConfig("h2mem1")
  val stock = TableQuery[StockTable]
  val setup = DBIO.seq(stock.schema.create)
  val setupFuture = db.run(setup)

  class StockTable(tag: Tag) extends Table[StockRecord](tag, "STOCK") {

    def * = (id, quantitiesCount, quantitiesState, sku, itemId) <> ((StockRecord.apply _).tupled, StockRecord.unapply)

    def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)

    def quantitiesCount = column[Int]("QUANTITIES_COUNT")

    def quantitiesState = column[String]("QUANTITIES_STATE")

    def sku = column[String]("SKU")

    def itemId = column[Int]("ITEM_ID")
  }

}
