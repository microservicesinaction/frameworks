package todomvc.core

import java.util.UUID

case class Todo(title: String, completed: Boolean, id: UUID = UUID.randomUUID)

object Todo {
  def create = Todo(title = "", completed = false)
}

// 
case class Price(value: Int, currency: String)
case class Colour(value: String)
case class AttributeTag(value: String)
case class Size(value: Int, region: String)
case class State(value: String)
case class Sku(value: String)
case class Item(price: Price, colour: Colour, tags: AttributeTag, size: Size, state: State, sku: Sku, id: UUID = UUID.randomUUID)
object Item {
	def create = Item(Price(5, "a"), Colour("b"), AttributeTag("c"), Size(2, "d"), State("e"), Sku("f"))
}