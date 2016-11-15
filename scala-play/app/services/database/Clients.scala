package services.database

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import slick.driver.JdbcProfile


case class ClientRecord(id: Int, firstName: String, lastName: String, petId: Int)
object ClientRecord {
  implicit val clientRecordFormat = Json.format[ClientRecord]
}

class ClientsRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db       = dbConfig.db

  import dbConfig.driver.api._
  val clients = TableQuery[ClientsTable]

  class ClientsTable(tag: Tag) extends Table[ClientRecord](tag, "CLIENTS") {

    def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    def firstName = column[String]("FIRST_NAME")
    def lastName = column[String]("LAST_NAME")
    def petId = column[Int]("PET_ID")

    def * = (id, firstName, lastName, petId) <> ((ClientRecord.apply _).tupled, ClientRecord.unapply)
  }
}