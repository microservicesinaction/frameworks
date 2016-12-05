package com.stockService.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

//todo: Would be good to get rid of the need to have "value" in json like we've done in Play

case class Price(value: Int, currency: String)

case class Colour(value: String)

case class AttributeTag(value: String)

case class Size(value: Int, region: String)

case class State(value: String)

case class Sku(value: String)

case class Item(id: Int, price: Price, colour: Colour, tags: AttributeTag, size: Size, state: State, sku: Sku)

object ItemJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val priceFormat = jsonFormat2(Price.apply)
  implicit val colourFormat = jsonFormat1(Colour.apply)
  implicit val attributeTagFormat = jsonFormat1(AttributeTag.apply)
  implicit val sizeFormat = jsonFormat2(Size.apply)
  implicit val stateFormat = jsonFormat1(State.apply)
  implicit val skuFormat = jsonFormat1(Sku.apply)

  implicit val itemFormat = jsonFormat7(Item.apply)
}

case class ItemRecord(id: Int, priceValue: Int, priceCurrency: String, colour: String, tags: String, sizeValue: Int, sizeRegion: String, state: String, sku: String) {
  def toItem: Item = Item(id = id, price = Price(priceValue, priceCurrency), colour = Colour(colour), tags = AttributeTag(tags), size = Size(sizeValue, sizeRegion), state = State(state), sku = Sku(sku))
}

object ItemRecordJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val itemRecordFormat = jsonFormat9(ItemRecord.apply)
}