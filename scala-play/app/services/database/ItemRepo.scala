package services.database

import com.google.inject.Inject
import models._
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

class ItemRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db       = dbConfig.db

  import dbConfig.driver.api._

  val items = TableQuery[ItemTable]

  class ItemTable(tag: Tag) extends Table[ItemRecord](tag, "ITEM") {

    def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    def priceValue = column[Int]("PRICE_VALUE")
    def priceCurrency = column[String]("PRICE_CURRENCY")
    def colour = column[String]("COLOUR")
    def tags = column[String]("TAGS")
    def sizeValue = column[Int]("SIZE_VALUE")
    def sizeRegion = column[String]("SIZE_REGION")
    def state = column[String]("STATE")
    def sku = column[String]("SKU")

    def * = (id, priceValue, priceCurrency, colour, tags, sizeValue, sizeRegion, state, sku) <> ((ItemRecord.apply _).tupled, ItemRecord.unapply)
  }
}
