package services.database

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import slick.driver.JdbcProfile

case class PetRecord(id: Int, name: String, notes: String)
object PetRecord {
  implicit val petRecordFormat = Json.format[PetRecord]
}

class PetsRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db       = dbConfig.db

  import dbConfig.driver.api._
  val pets     = TableQuery[PetsTable]

  class PetsTable(tag: Tag) extends Table[PetRecord](tag, "PETS") {
    def id    = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    def name  = column[String]("NAME")
    def notes = column[String]("NOTES")

    def *     = (id, name, notes) <> ((PetRecord.apply _).tupled, PetRecord.unapply)
  }
}
