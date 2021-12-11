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
    implicit val newTodoItemJsonFormat = Json.format[NewTodoItem]

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

    def newItem() = Action { implicit request =>
        val jsonObject = request.body.asJson
        val newTodoItem: Option[NewTodoItem] = jsonObject.flatMap(Json.fromJson[NewTodoItem](_).asOpt)

        newTodoItem match {
            case Some(newItem) =>
                val nextId = todoList.map(_.id).max + 1
                val toBeAdded = TodoListItem(nextId, newItem.description, false)
                todoList += toBeAdded
                Created(Json.toJson(toBeAdded))
            case None =>
                BadRequest
        }
    }

}
