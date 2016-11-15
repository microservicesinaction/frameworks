package services.databases

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import services.database.ClientRecord

class ClientsRepoSpec extends PlaySpec {

  "Clients" should {
    "write to Json" in {
      Json.toJson(ClientRecord(1, "sofia", "cole", 1))
    }
  }

}
