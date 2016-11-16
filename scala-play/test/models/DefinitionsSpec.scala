package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsValue, Json}
import org.scalatest.Matchers._

class DefinitionsSpec extends PlaySpec {

  "Item Formats" should {
    "write to Json" in {
      Json.toJson(ItemRecord(1, 100, "GBP", "Red", "Large Sizing", 10, "EUR", "NoIdea", "134-1435-53"))
    }

    "get Item from Json" in {

      val json: JsValue = Json.parse(
        s"""
           |{
           |  "id": 1,
           |  "price": {
           |    "value": 12,
           |    "currency": "GBP"
           |   },
           |  "colour": {
           |    "value": "colour"
           |  },
           |  "tags": {
           |    "value": "tags"
           |  },
           |  "size": {
           |    "value": 100,
           |    "region": "EU"
           |  },
           |  "state": {
           |    "value": "state"
           |  },
           |  "sku": {
           |    "value": "sku"
           |  }
           |}
           |
         """.stripMargin)

      Json.fromJson[Item](json).get shouldBe Item(1, Price(12, "GBP"), Colour("colour"), AttributeTag("tags"), Size(100, "EU"), State("state"), Sku("sku"))
    }
  }

}
