package services.database

import com.google.inject.Inject
import models.StockRecord
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

class StockRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db       = dbConfig.db

  import dbConfig.driver.api._

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
