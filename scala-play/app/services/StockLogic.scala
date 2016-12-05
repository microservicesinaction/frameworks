package services

import com.google.inject.Inject
import models.{Stock, StockRecord}
import services.database.StockRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class StockLogic @Inject()(repo: StockRepo) {

  import repo.dbConfig.driver.api._

  def addStock(itemId: Int, stock: Stock): Future[String] = {
    repo.db.run(
      repo.stock += StockRecord(
        -1,
        stock.quantities.count,
        stock.quantities.state,
        stock.sku.value,
        itemId
      )
    ).map(_ => "Success!")
  }

  def deleteStock(itemId: Int): Future[String] = {
    repo.db.run(
      repo.stock.filter(_.itemId === itemId).delete
    ).map(_ => "Success!")
  }
}
