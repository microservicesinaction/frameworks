package models

import play.api.libs.json.{Format, Json}
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Price(value: Int, currency: String)
object     Price { implicit val priceFormat = Json.format[Price] }

case class Colour(value: String)
object     Colour { implicit val colourFormat: Format[Colour] = Json.format[Colour] }

case class AttributeTag(value: String)
object     AttributeTag { implicit val attributeTagFormat: Format[AttributeTag] = Json.format[AttributeTag] }

case class Size(value: Int, region: String)
object     Size { implicit val sizeFormat = Json.format[Size] }

case class State(value: String)
object     State { implicit val stateFormat = Json.format[State] }

case class Sku(value: String)
object     Sku { implicit val skuFormat = Json.format[Sku] }

//todo multiple tags
case class Item(id: Int, price: Price, colour: Colour, tags: AttributeTag, size: Size, state: State, sku: Sku)
object     Item {

  val itemReads: Reads[Item] = (
      (JsPath \ "id").read[Int] and
      (JsPath \ "price").read[Price] and
      (JsPath \ "colour").read[String].map { value => Colour(value) } and
      (JsPath \ "tags").read[String].map { value => AttributeTag(value) } and
      (JsPath \ "size").read[Size] and
      (JsPath \ "state").read[String].map { value => State(value) } and
      (JsPath \ "sku").read[String].map { value => Sku(value) }
    )(Item.apply _)


  val itemWrites: Writes[Item] = (
      (JsPath \ "id").write[Int] and
      (JsPath \ "price").write[Price] and
      (JsPath \ "colour").write[String].contramap { (colour: Colour) => colour.value } and
      (JsPath \ "tags").write[String].contramap { (tag: AttributeTag) => tag.value } and
      (JsPath \ "size").write[Size] and
      (JsPath \ "state").write[String].contramap { (state: State) => state.value } and
      (JsPath \ "sku").write[String].contramap { (sku: Sku) => sku.value }
    )(unlift(Item.unapply))

  implicit val itemFormat: Format[Item] =
    Format(itemReads, itemWrites)
}

case class ItemRecord(id: Int, priceValue: Int, priceCurrency: String, colour: String, tags: String, sizeValue: Int, sizeRegion: String, state: String, sku: String) {
  def toItem: Item = Item(id = id, price = Price(priceValue, priceCurrency), colour = Colour(colour), tags = AttributeTag(tags), size = Size(sizeValue, sizeRegion), state = State(state), sku = Sku(sku))
}
