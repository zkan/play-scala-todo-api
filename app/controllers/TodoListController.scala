package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.collection.mutable.ListBuffer
import models._

@Singleton
class TodoListController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
    private val todoList = new ListBuffer[TodoListItem]()
    todoList += TodoListItem(1, "Buy new laptop", true)
    todoList += TodoListItem(2, "Call mom", false)

    implicit val todoItemJsonFormat = Json.format[TodoListItem]

    def all(): Action[AnyContent] = Action {
        if (todoList.isEmpty) {
            NoContent
        } else {
            Ok(Json.toJson(todoList))
        }
    }

    def byId(itemId: Int): Action[AnyContent] = Action {
        val result = todoList.find(_.id == itemId)
        result match {
            case Some(item) => Ok(Json.toJson(item))
            case None => NotFound
        }
    }
}
