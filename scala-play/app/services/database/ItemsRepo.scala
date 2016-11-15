package services.database

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import slick.driver.JdbcProfile


case class ItemRecord(id: Int, firstName: String, lastName: String, petId: Int)
object ItemRecord {
  implicit val itemRecordFormat = Json.format[ItemRecord]
}

class ItemsRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db       = dbConfig.db

  import dbConfig.driver.api._
  val items = TableQuery[ItemsTable]

  class ItemsTable(tag: Tag) extends Table[ItemRecord](tag, "ITEMS") {

    def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    def firstName = column[String]("FIRST_NAME")
    def lastName = column[String]("LAST_NAME")
    def petId = column[Int]("PET_ID")

    def * = (id, firstName, lastName, petId) <> ((ItemRecord.apply _).tupled, ItemRecord.unapply)
  }
}