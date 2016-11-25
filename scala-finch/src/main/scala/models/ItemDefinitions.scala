package models

case class Price(value: Int, currency: String)
case class Colour(value: String)
case class AttributeTag(value: String)
case class Size(value: Int, region: String)
case class State(value: String)
case class Sku(value: String)
case class Item(id: Int, price: Price, colour: Colour, tags: AttributeTag, size: Size, state: State, sku: Sku)