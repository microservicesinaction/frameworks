package models

import org.scalatest.Matchers._
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsValue, Json}

class ItemDefinitionsSpec extends PlaySpec {

  "Item Formats" should {

    val json: JsValue = Json.parse(
      s"""
         |{
         |"id": 1,
         |"price": {
         |"value": 100,
         |"currency": "GBP"
         |},
         |"colour": "Red",
         |"tags": "Large Sizing",
         |"size": {
         |"value": 10,
         |"region": "EUR"
         |},
         |"state": "NoIdea",
         |"sku": "134-1435-53"
         |}
         |
         """.stripMargin)

    val item: Item = Item(Some(1), Price(100, "GBP"), Colour("Red"), AttributeTag("Large Sizing"), Size(10, "EUR"), State("NoIdea"), Sku("134-1435-53"))


    "write to Json" in {
      Json.toJson(item) shouldBe json
    }

    "get Item from Json" in {
      json.as[Item] shouldBe item
    }
  }
}