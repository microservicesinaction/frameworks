package services

import com.google.inject.Inject
import services.database.{PetRecord, PetsRepo}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Pet(name: String, notes: String)

class PetLogic @Inject()(petsRepo: PetsRepo) {
  import petsRepo.dbConfig.driver.api._

  def addPet(newPet: Pet): Future[Int] =
    petsRepo.db.run((petsRepo.pets returning petsRepo.pets.map(_.id)) += PetRecord(0, newPet.name, newPet.notes))

  def returnAllPets(): Future[List[Pet]] =
    petsRepo.db.run(petsRepo.pets.result).map(_.map(pet => Pet(pet.name, pet.notes)).toList)
}