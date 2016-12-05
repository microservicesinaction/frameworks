package com.stockService

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.stockService.logic.{ItemLogic, StockLogic}
import com.stockService.models.{Item, Stock}
import com.typesafe.config.Config

import scala.concurrent.ExecutionContextExecutor

trait StockService {

  import com.stockService.models.ItemJsonProtocol._

  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: Materializer
  val logger: LoggingAdapter
  val itemLogic = new ItemLogic
  val stockLogic = new StockLogic
  val routes: Route = {
    logRequestResult("akka-http-microservice") {
      path("items") {
        get {
          complete {
            itemLogic.getItems(None, None, None).map[ToResponseMarshallable] {
              case value => value
            }
          }
        } ~
          post {
            entity(as[Item]) { item =>
              complete {
                itemLogic.registerItem(item).map[ToResponseMarshallable] {
                  case value => value
                }
              }
            }
          }
      } ~
        path("items" / IntNumber) { id =>
          get {
            complete {
              itemLogic.getItem(id).map[ToResponseMarshallable] {
                case value => value
              }
            }
          } ~
            put {
              entity(as[Item]) { item =>
                complete {
                  itemLogic.updateItem(id, item).map[ToResponseMarshallable] {
                    case value => value
                  }
                }
              }
            } ~
            delete {
              complete {
                itemLogic.deleteItem(id).map[ToResponseMarshallable] {
                  case value => value
                }
              }
            }
        } ~
        path("stock" / IntNumber) { itemId =>
          put {
            import com.stockService.models.StockJsonProtocol._
            entity(as[Stock]) { stock =>
              complete {
                stockLogic.addStock(itemId, stock).map[ToResponseMarshallable] {
                  case value => value
                }
              }
            }
          }
        }
    }
  }

  def config: Config
}