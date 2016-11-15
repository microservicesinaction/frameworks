package controllers

import com.google.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.database.{Client, ClientLogic}
import services.{Pet, PetLogic}
import services.muretail.{Item, ItemService}

import scala.concurrent.ExecutionContext.Implicits.global

case class RegisterItem(firstName: String, lastName: String, petName: String, petNotes: String)
object RegisterItem {
  implicit val registerFormat = Json.format[RegisterItem]
}

class Application @Inject()(clientsLogic: ClientLogic, petsLogic: PetLogic, itemService: ItemService) extends Controller {

  def registerNewPet = Action.async { request =>
    val registerItem = request.body.asJson.get.as[RegisterItem]
    for {
      petId <- petsLogic.addPet(Pet(registerItem.petName, registerItem.petNotes))
      _ <- itemService.addItem(Item(registerItem.firstName, registerItem.lastName, petId))
      result <- clientsLogic.addClient(Client(registerItem.firstName, registerItem.lastName, petId))
    } yield Ok("Success")
  }

  def getAllClients = Action.async {
    clientsLogic.returnAllClients().map(client => Ok(client.mkString(", ")))
  }

  def getAllPets = Action.async {
    petsLogic.returnAllPets().map(pet => Ok(pet.mkString(", ")))
  }

  def getAllItems = Action.async {
    itemService.returnAllItems().map(item => Ok(item.mkString(", ")))
  }
}
