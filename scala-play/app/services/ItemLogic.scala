package services.database

import com.google.inject.Inject
import models._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ItemLogic @Inject()(repo: ItemRepo) {
  import repo.dbConfig.driver.api._

  def getItems(detail: Boolean, start: String, size: Int): Future[List[Item]] = {
    repo.db.run(
      repo.items
        .filter(_.sizeValue === size)
        .result
    ).map(_.map(_.toItem).toList)
  }

  def getItem(id: Int): Future[Item] = {
    repo.db.run(
      repo.items.filter(_.id === id).result
    ).map{ _.headOption match {
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
}
