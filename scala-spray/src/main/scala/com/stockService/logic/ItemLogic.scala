package com.stockService.logic

import com.stockService.db.ItemRepo
import com.stockService.models.{Item, ItemRecord}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ItemLogic {

  import slick.driver.H2Driver.api._

  val repo = new ItemRepo

  def getItems(detail: Option[Boolean], start: Option[String], size: Option[Int]): Future[List[Item]] = {
    repo.db.run(
      repo.items
        .filter(f =>
          size.map(s =>
            f.sizeValue === s).getOrElse(slick.lifted.LiteralColumn(true)))
        .result
    ).map(_.map(_.toItem).toList)
  }

  def getItem(id: Int): Future[Item] = {
    repo.db.run(
      repo.items.filter(_.id === id).result
    ).map {
      _.headOption match {
        case Some(result) => result.toItem
        case None => ??? //todo
      }
    }
  }

  def updateItem(id: Int, item: Item): Future[String] = {
    repo.db.run(
      repo.items.insertOrUpdate(
        ItemRecord(
          id,
          item.price.value,
          item.price.currency,
          item.colour.value,
          item.tags.value,
          item.size.value,
          item.size.region,
          item.state.value,
          item.sku.value)
      )
    ).map(_ => "Success!")
  }

  def registerItem(item: Item): Future[String] = {
    repo.db.run(
      repo.items += ItemRecord(
        -1,
        item.price.value,
        item.price.currency,
        item.colour.value,
        item.tags.value,
        item.size.value,
        item.size.region,
        item.state.value,
        item.sku.value)
    ).map(_ => "Success!")
  }

  def deleteItem(id: Int): Future[String] = {
    val stockLogic = new StockLogic
    stockLogic.deleteStock(id)
    repo.db.run(
      repo.items.filter(_.id === id).delete
    ).map(_ => "Success!")
  }
}

