package models

import play.api.libs.json.Json

//todo get rid of needing to put "value" in json

case class Price(value: Int, currency: String)

object Price {
  implicit val priceFormat = Json.format[Price]
}

case class Colour(value: String)

object Colour {
  implicit val colourFormat = Json.format[Colour]
}

case class AttributeTag(value: String)

object AttributeTag {
  implicit val attributeTagFormat = Json.format[AttributeTag]
}

case class Size(value: Int, region: String)

object Size {
  implicit val sizeFormat = Json.format[Size]
}

case class State(value: String)

object State {
  implicit val stateFormat = Json.format[State]
}

case class Sku(value: String)

object Sku {
  implicit val skuFormat = Json.format[Sku]
}

//todo multiple tags
case class Item(id: Int, price: Price, colour: Colour, tags: AttributeTag, size: Size, state: State, sku: Sku)

object Item {
  implicit val itemFormat = Json.format[Item]
}

case class ItemRecord(id: Int, priceValue: Int, priceCurrency: String, colour: String, tags: String, sizeValue: Int, sizeRegion: String, state: String, sku: String) {
  def toItem: Item =
    Item(id = id, price = Price(priceValue, priceCurrency), colour = Colour(colour), tags = AttributeTag(tags), size = Size(sizeValue, sizeRegion), state = State(state), sku = Sku(sku))
}

object ItemRecord {
  implicit val itemRecordFormat = Json.format[ItemRecord]
}
