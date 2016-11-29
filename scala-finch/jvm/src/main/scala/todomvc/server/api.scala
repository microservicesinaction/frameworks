package todomvc.server

import com.twitter.util.Future
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._
import java.util.UUID
import todomvc.core._

class TodoApi(db: TodoDatabase) {
  import AsyncImplicits._

  val seedItem = Item(Price(1, "a"), Colour("b"), AttributeTag("c"), Size(2, "d"), State("e"), Sku("f"))
  val itemRepo = collection.mutable.Map[UUID, Item](
                    seedItem.id -> seedItem
  )

  lazy val listEndpoint = get("todo") {
    db.list.toFuture map (Ok(_))
  }

  val createEndpoint = post("todo" :: body.as[UUID => Todo]) { (create: UUID => Todo) =>
    db.save(create(UUID.randomUUID)).toFuture map (Ok(_))
  }

  val readEndpoint = get("todo" / uuid) { (id: UUID) =>
    db.find(id).toFuture map {
      case Some(todo) => Ok(todo)
      case None       => notFound(id)
    }
  }

  val updateEndpoint = put("todo" / uuid :: body.as[UUID => Todo]) { (id: UUID, update: UUID => Todo) =>
    db.find(id).toFuture flatMap {
      case Some(todo) => db.save(update(todo.id)).toFuture.map(Ok(_))
      case None       => Future.value(notFound(id))
    }
  }

  val deleteEndpoint = delete("todo" / uuid) { (id: UUID) =>
    db.delete(id).toFuture map { deleted =>
      if(deleted) Ok(()) else notFound(id)
    }
  }

  val syncEndpoint = put("todo" :: body.as[List[Todo]]) { (todos: List[Todo]) =>
    db.sync(todos).toFuture map (Ok(_))
  }

  val preflightEndpoint = options(*) { Ok() }

  // Item Endpoints...
  lazy val itemsFilter =  paramOption("detail").as[Boolean].withDefault(false) :: 
                          paramOption("start").as[String].withDefault("") ::
                          paramOption("size").as[Int].withDefault(0)
  lazy val listItems = get("items" ? itemsFilter) { (a: Boolean, b: String, c: Int) =>
    Ok(itemRepo.values)
  }
  val retrieveItem = get("items" / uuid) { uuid: UUID =>
    Ok(itemRepo.get(uuid))
  }
  val createItem = post("items" :: body.as[UUID => Item]) { (create: UUID => Item) =>
    val uuid = UUID.randomUUID
    itemRepo += (uuid -> create(uuid))
    Created(uuid)
  }
  val updateItem = put("items" / uuid :: body.as[Item]) { (uuid: UUID, item: Item) =>
    val updatedItem = Item(id = uuid, price = item.price, colour = item.colour, tags = item.tags, size = item.size, state = item.state, sku = item.sku)
    itemRepo += (uuid -> updatedItem)
    Ok(uuid)
  }
  val deleteItem = delete("items" / uuid) { (id: UUID) =>
    itemRepo.remove(id)
    Ok("Deleted: " + id)
  }
  // End of Item Endpoints


  implicit class EndpointOps[A](endpoint: Endpoint[A]) {
    def withHeaders(headers: Map[String, String]): Endpoint[A] =
      headers.foldLeft(endpoint)((e, h) => e.withHeader(h._1, h._2))
  }

  val corsHeaders = Map(
    "Access-Control-Allow-Origin"  -> "*",
    "Access-Control-Allow-Methods" -> "GET,POST,PUT,DELETE,HEAD,OPTIONS",
    "Access-Control-Max-Age"       -> "300",
    "Access-Control-Allow-Headers" -> "Origin,X-Requested-With,Content-Type,Accept"
  )

  val endpoints = (
    listEndpoint   :+:
    createEndpoint :+:
    readEndpoint   :+:
    updateEndpoint :+:
    deleteEndpoint :+:
    syncEndpoint   :+:
    listItems :+:
    retrieveItem :+:
    createItem :+:
    updateItem :+:
    deleteItem :+:
    preflightEndpoint
  ) withHeaders (corsHeaders)

  val service = endpoints.toService

  private[todomvc] def notFound(id: UUID) =
    NotFound(new RuntimeException(s"Todo item not found: ${id}"))
}