package com.stockService.logic

import com.stockService.db.StockRepo
import com.stockService.models.{Stock, StockRecord}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class StockLogic {

  import slick.driver.H2Driver.api._

  val repo = new StockRepo

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
