package com.stockService.logic

import com.stockService.{Stock, StockRecord}
import com.stockService.db.{ItemRepo, StockRepo}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

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
        itemId,
        stock.stockItems.state
      )
    ).map(_ => "Success!")
  }

}
