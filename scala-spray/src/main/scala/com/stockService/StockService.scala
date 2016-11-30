package com.stockService

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import com.stockService.logic.ItemLogic

import scala.util.{Failure, Success}

class StockServiceActor extends Actor with StockService {
  def actorRefFactory = context

  def receive = runRoute(myRoute)
}

trait StockService extends HttpService {
  import spray.httpx.SprayJsonSupport._
  import ItemJsonProtocol._

  private implicit def ec = actorRefFactory.dispatcher

  val itemLogic = new ItemLogic
  val stockLogic = new StockLogic

  val myRoute =
    path("items") {
      get {
        respondWithMediaType(`application/json`) {
          onComplete(itemLogic.getItems(None, None, None)) {
            case Success(value) => complete(value)
            case Failure(ex)    => complete("Uh Oh")
          }
        }
      } ~
      post {
        entity(as[Item]) { item =>
          onComplete(itemLogic.registerItem(item)) {
            case Success(value) => complete(value)
            case Failure(ex)    => complete("Uh oh")
          }
        }
      }
    } ~
    path("items" / IntNumber) { id =>
      get {
       respondWithMediaType(`application/json`) {
         onComplete(itemLogic.getItem(id)) {
           case Success(value) => complete(value)
           case Failure(ex)    => complete("Uh oh")
         }
       }
      } ~
      put {
        entity(as[Item]) { item =>
          onComplete(itemLogic.updateItem(id, item)) {
            case Success(value) => complete(value)
            case Failure(ex)    => complete("Uh oh")
          }
        }
      } ~
      delete {
        onComplete(itemLogic.deleteItem(id)) {
          case Success(value) => complete(value)
          case Failure(ex)    => complete("Uh oh")
        }
      }
    } ~
    path("stock" / IntNumber) { id =>
      put {
        entity(as[Item]) { item =>
          onComplete(stockLogic.updateItem(id, item)) {
            case Success(value) => complete(value)
            case Failure(ex)    => complete("Uh oh")
          }
        }
      }
    }
}