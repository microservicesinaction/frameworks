package com.stockService

import spray.json.DefaultJsonProtocol

//todo: Would be good to get rid of the need to have "value" in json like we've done in Play

case class Price(value: Int, currency: String)

case class Colour(value: String)

case class AttributeTag(value: String)

case class Size(value: Int, region: String)

case class State(value: String)

case class Sku(value: String)

case class Item(id: Int, price: Price, colour: Colour, tags: AttributeTag, size: Size, state: State, sku: Sku)
object ItemJsonProtocol extends DefaultJsonProtocol {
  implicit val PriceFormat = jsonFormat2(Price)
  implicit val ColourFormat = jsonFormat1(Colour)
  implicit val AttributeTagFormat = jsonFormat1(AttributeTag)
  implicit val SizeFormat = jsonFormat2(Size)
  implicit val StateFormat = jsonFormat1(State)
  implicit val SkuFormat = jsonFormat1(Sku)

  implicit val ItemFormat = jsonFormat7(Item)
}

case class ItemRecord(id: Int, priceValue: Int, priceCurrency: String, colour: String, tags: String, sizeValue: Int, sizeRegion: String, state: String, sku: String) {
  def toItem: Item = Item(id = id, price = Price(priceValue, priceCurrency), colour = Colour(colour), tags = AttributeTag(tags), size = Size(sizeValue, sizeRegion), state = State(state), sku = Sku(sku))
}

object ItemRecordJsonProtocol extends DefaultJsonProtocol {
  implicit val ItemRecordFormat = jsonFormat9(ItemRecord)
}