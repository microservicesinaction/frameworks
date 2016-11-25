package service

import models._

import com.twitter.finagle.Http
import com.twitter.util.Await

import io.finch._
import io.finch.circe._
import io.circe.generic.auto._

object ItemService extends App {

  // GET     /items              controllers.Application.getItems(detail: Option[Boolean], start: Option[String], size: Option[Int])
  val itemsFilter = paramOption("detail").as[Boolean].withDefault(false) :: 
                    paramOption("start").as[String].withDefault("") ::
                    paramOption("size").as[Int].withDefault(0)
  val getItems: Endpoint[List[Item]] = 
    get("items" ? itemsFilter) { (a: Boolean, b: String, c: Int) =>
      Ok(List(Item(c, Price(5, "a"), Colour("b"), AttributeTag("c"), Size(2, "d"), State("e"), Sku("f"))))
    }

  // GET  /items/:id 
  val getItemById: Endpoint[Item] = 
    get("items" / int) { id: Int =>
      Ok(new Item(id, Price(5, "a"), Colour("b"), AttributeTag("c"), Size(2, "d"), State("e"), Sku("f")))
    }

  // PUT     /items/:id
  val updateItem: Endpoint[String] = 
    put("items" / int) { id: Int =>
      Ok("")
    }

  // POST    /items              controllers.Application.registerItem
  val addItem: Endpoint[String] = 
    post("items" / int) { id: Int =>
      Ok("")
    }

  // DELETE  /items/:id          controllers.Application.deleteItem(id: Int)
  val deleteItem: Endpoint[String] = 
    delete("items" / int) { id: Int =>
      Ok("")
    }

  Await.ready(Http.serve(":8081", (getItems :+: getItemById :+: updateItem :+: addItem :+: deleteItem).toService))
}