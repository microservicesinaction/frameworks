package com.stockService.db

import com.stockService.models.ItemRecord
import slick.driver.H2Driver.api._
import slick.lifted.Tag

class ItemRepo {
  //todo object?

  val items = TableQuery[ItemTable]
  val db = Database.forConfig("h2mem1")
  val setup = DBIO.seq(items.schema.create)
  val setupFuture = db.run(setup)

  class ItemTable(tag: Tag) extends Table[ItemRecord](tag, "ITEMS") {

    def * = (id, priceValue, priceCurrency, colour, tags, sizeValue, sizeRegion, state, sku) <> ((ItemRecord.apply _).tupled, ItemRecord.unapply)

    def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)

    def priceValue = column[Int]("PRICE_VALUE")

    def priceCurrency = column[String]("PRICE_CURRENCY")

    def colour = column[String]("COLOUR")

    def tags = column[String]("TAGS")

    def sizeValue = column[Int]("SIZE_VALUE")

    def sizeRegion = column[String]("SIZE_REGION")

    def state = column[String]("STATE")

    def sku = column[String]("SKU")
  }
}