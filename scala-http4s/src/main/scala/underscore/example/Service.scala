package underscore.example

import org.http4s._
import org.http4s.server._
import org.http4s.dsl._
import org.http4s.argonaut._
import _root_.argonaut._
import java.util.UUID

object ItemService {

  val seedItem = Item(Price(1, "a"), Colour("b"), AttributeTag("c"), Size(2, "d"), State("e"), Sku("f"))
  val itemRepo = collection.mutable.Map[String, Item]( seedItem.id -> seedItem )

  val service = HttpService {
    case GET -> Root =>
     Ok(itemRepo.values.toList)
    case GET -> Root / id =>
      itemRepo.get(id) match {
        case Some(x) => Ok(x)
        case None => NotFound()
      }
    case req @ POST -> Root => 
      req.decode[Item] { item: Item =>
        val uuid = UUID.randomUUID.toString
        val createdItem = Item(id = uuid, price = item.price, colour = item.colour, tags = item.tags, size = item.size, state = item.state, sku = item.sku)
        itemRepo += (uuid -> createdItem)
        Created(uuid.toString) // return id
      }
    case req @ PUT -> Root / id =>
      req.decode[Item] { item: Item =>
        val updatedItem = Item(id = id, price = item.price, colour = item.colour, tags = item.tags, size = item.size, state = item.state, sku = item.sku)
        itemRepo += (id -> updatedItem)
        Ok(id) // should return id
      }
    case DELETE -> Root / id =>
      itemRepo remove (id)
      Gone(id) // 
  }
}

// Items domain model
import java.util.UUID
final case class Price(value: Int, currency: String)
object Price {
  implicit val priceCodecJson: CodecJson[Price] = Argonaut.casecodec2(Price.apply, Price.unapply)("value", "currency")
  implicit val priceEntityDecoder: EntityDecoder[Price] = jsonOf[Price]
  implicit val priceEntityEncoder: EntityEncoder[Price] = jsonEncoderOf[Price]
}
final case class Colour(value: String)
object Colour {
  implicit val colourCodecJson: CodecJson[Colour] = Argonaut.casecodec1(Colour.apply, Colour.unapply)("value")
  implicit val colourEntityDecoder: EntityDecoder[Colour] = jsonOf[Colour]
  implicit val colourEntityEncoder: EntityEncoder[Colour] = jsonEncoderOf[Colour]
}
final case class AttributeTag(value: String)
object AttributeTag {
  implicit val tagCodecJson: CodecJson[AttributeTag] = Argonaut.casecodec1(AttributeTag.apply, AttributeTag.unapply)("value")
  implicit val tagEntityDecoder: EntityDecoder[AttributeTag] = jsonOf[AttributeTag]
  implicit val tagEntityEncoder: EntityEncoder[AttributeTag] = jsonEncoderOf[AttributeTag]
}
final case class Size(value: Int, region: String)
object Size {
  implicit val sizeCodecJson: CodecJson[Size] = Argonaut.casecodec2(Size.apply, Size.unapply)("value", "region")
  implicit val sizeEntityDecoder: EntityDecoder[Size] = jsonOf[Size]
  implicit val sizeEntityEncoder: EntityEncoder[Size] = jsonEncoderOf[Size]
}
final case class State(value: String)
object State {
  implicit val stateCodecJson: CodecJson[State] = Argonaut.casecodec1(State.apply, State.unapply)("value")
  implicit val stateEntityDecoder: EntityDecoder[State] = jsonOf[State]
  implicit val stateEntityEncoder: EntityEncoder[State] = jsonEncoderOf[State]
}
final case class Sku(value: String)
object Sku {
  implicit val skuCodecJson: CodecJson[Sku] = Argonaut.casecodec1(Sku.apply, Sku.unapply)("value")
  implicit val skuEntityDecoder: EntityDecoder[Sku] = jsonOf[Sku]
  implicit val skuEntityEncoder: EntityEncoder[Sku] = jsonEncoderOf[Sku]
}
final case class Item(price: Price, colour: Colour, tags: AttributeTag, size: Size, state: State, sku: Sku, id: String = UUID.randomUUID.toString)
object Item {
  implicit val itemCodecJson: CodecJson[Item] = Argonaut.casecodec7(Item.apply, Item.unapply)("price", "colour", "tags", "size", "state", "sku", "id")
  implicit val itemEntityDecoder: EntityDecoder[Item] = jsonOf[Item]
  implicit val itemEntityEncoder: EntityEncoder[Item] = jsonEncoderOf[Item]
  implicit val litsItemEntityDecoder: EntityDecoder[List[Item]] = jsonOf[List[Item]]
  implicit val listItemEntityEncoder: EntityEncoder[List[Item]] = jsonEncoderOf[List[Item]]
  def create = Item(Price(5, "a"), Colour("b"), AttributeTag("c"), Size(2, "d"), State("e"), Sku("f"))
}