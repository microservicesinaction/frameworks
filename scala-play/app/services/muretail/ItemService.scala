package services.muretail

import com.google.inject.Inject
import play.api.libs.json.Json
import services.database.{ItemRecord, ItemsRepo}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Item(firstName: String, lastName: String, petId: Int)
object Item {
  implicit val itemFormat = Json.format[Item]
}

class ItemService @Inject()(itemsRepo: ItemsRepo) {
  import itemsRepo.dbConfig.driver.api._

  def addItem(item: Item): Future[Unit] =
    itemsRepo.db.run(itemsRepo.items += ItemRecord(0, item.firstName, item.lastName, item.petId)).map(_=>())

  def returnAllItems(): Future[List[Item]] =
    itemsRepo.db.run(itemsRepo.items.result).map(_.map(item => Item(item.firstName, item.lastName, item.petId)).toList)
}
