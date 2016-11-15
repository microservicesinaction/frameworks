package services.database

import com.google.inject.Inject
import play.api.libs.json.Json

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Client(firstName: String, lastName: String, petId: Int)
object Client {
  implicit val clientFormat = Json.format[Client]
}

class ClientLogic @Inject()(clientsRepo: ClientsRepo) {
  import clientsRepo.dbConfig.driver.api._

  def addClient(newClient: Client): Future[Unit] =
    clientsRepo.db.run(clientsRepo.clients += ClientRecord(0, newClient.firstName, newClient.lastName, newClient.petId)).map(_=>())

  def returnAllClients(): Future[List[Client]] =
    clientsRepo.db.run(clientsRepo.clients.result).map(_.map(client => Client(client.firstName, client.lastName, client.petId)).toList)
}
