package models

import org.scalatest.Matchers._
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsValue, Json}

class StockDefinitionsSpec extends PlaySpec {

  "Stock Formats" should {

    val json: JsValue = Json.parse(
      s"""
         |{
         |  "quantities": {
         |    "count": 12,
         |    "state": "Full"
         |   },
         |  "sku": "102/305"
         |}
         |
         """.stripMargin)

    val stock = Stock(Quantities(12, "Full"), Sku("102/305"))


    "write to Json" in {
      Json.toJson(stock) shouldBe json
    }

    "get Stock from Json" in {
      json.as[Stock] shouldBe stock
    }
  }

}

