package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsValue, Json}
import org.scalatest.Matchers._
import play.api.mvc._



class DefinitionsSpec extends PlaySpec {

  "Item Formats" should {
    "write to Json" in {
      Json.toJson(Item(1, Price(100, "GBP"), Colour("Red"), AttributeTag("Large Sizing"), Size(10, "EUR"), State("NoIdea"), Sku("134-1435-53")))
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
           |  "colour": "colour",
           |  "tags": "tags",
           |  "size": {
           |    "value": 100,
           |    "region": "EU"
           |  },
           |  "state": "state",
           |  "sku": "sku"
           |}
           |
         """.stripMargin)

      json.as[Item] shouldBe Item(1, Price(12, "GBP"), Colour("colour"), AttributeTag("tags"), Size(100, "EU"), State("state"), Sku("sku"))
    }
  }

}
