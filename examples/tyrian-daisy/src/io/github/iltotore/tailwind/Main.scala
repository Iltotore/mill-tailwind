package io.github.iltotore.tailwind

import cats.effect.IO
import tyrian.Html.*
import tyrian.SVG.*
import tyrian.*

import scala.scalajs.js.annotation.*

/* 
Generated classes so they can be scanned by Tailwind:
*/

@JSExportTopLevel("TyrianApp")
object Counter extends TyrianIOApp[TodoMsg, TodoList]:

  def router: Location => TodoMsg = Routing.none(TodoMsg.NoOp)

  def init(flags: Map[String, String]): (TodoList, Cmd[IO, TodoMsg]) =
    (TodoList.init, Cmd.None)

  def update(model: TodoList): TodoMsg => (TodoList, Cmd[IO, TodoMsg]) =
    case TodoMsg.SetListTitle(title) => (model.copy(title = title), Cmd.None)
    case TodoMsg.SetCurrentTitle(title) => (model.copy(currentTitle = title), Cmd.None)
    case TodoMsg.AddEntry => (model.newEntry, Cmd.None)
    case TodoMsg.RemoveEntry(id) => (model.removeEntry(id), Cmd.None)
    case TodoMsg.SetEntryTitle(id, title) => (model.updateEntry(id)(_.copy(title = title)), Cmd.None)
    case TodoMsg.SetEntryDescription(id, description) =>
      (model.updateEntry(id)(_.copy(description = description)), Cmd.None)
    case TodoMsg.ToogleCompleted(id) => (model.updateEntry(id)(_.toogleCompleted), Cmd.None)
    case TodoMsg.ToogleOpen(id) => (model.updateEntry(id)(_.toogleOpened), Cmd.None)
    case TodoMsg.NoOp => (model, Cmd.None)

  def subscriptions(model: TodoList): Sub[IO, TodoMsg] =
    Sub.None

  def svgButton(onClickMsg: TodoMsg, addClasses: String = "")(elements: Elem[TodoMsg]*): Html[TodoMsg] =
    svg(
      xmlns := "http://www.w3.org/2000/svg",
      fill := "none",
      viewBox := "0 0 24 24",
      cls := s"stroke-base-content hover:fill-base-content hover:stroke-white $addClasses",
      onClick(onClickMsg)
    )(elements*)

  def todoEntry(entry: TodoEntry, id: Int): Html[TodoMsg] =
    val openAttr =
      if entry.opened then "collapse-open"
      else "collapse-close"

    val borderColor =
      if entry.completed then "border-success"
      else "border-base-300"

    div(
      tabindex := 0,
      cls := s"collapse $openAttr collapse-arrow bg-base-100 $borderColor border"
    )(
      div(
        cls := "collapse-title font-semibold flex flex-row items-center justify-between",
        onClick(TodoMsg.ToogleOpen(id)),
      )(
        input(
          tpe := "text",
          value := entry.title,
          placeholder := "Task title",
          cls := s"border-b-1 $borderColor",
          onClick(TodoMsg.NoOp),
          onInput(TodoMsg.SetEntryTitle(id, _)),
        ),
        div(cls := "flex flex-row items-center gap-4")(
          input(
            tpe := "checkbox",
            checked := entry.completed,
            cls := "checkbox checkbox-success",
            onClick(TodoMsg.ToogleCompleted(id)),
          ),
          svgButton(TodoMsg.RemoveEntry(id), "size-6")(
            path(
              d := "m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
            )
          )
        )
      ),
      textarea(
        placeholder := "Task description",
        cls := s"w-full collapse-content text-sm resize-none",
        onInput(TodoMsg.SetEntryDescription(id, _))
      )(entry.description)
    )

  val addBox: Html[TodoMsg] =
    div(
      cls := "w-full rounded-lg flex flex-row items-center justify-between bg-base-100 border-base-300 border p-4"
    )(
      input(
        placeholder := "Add new task...",
        cls := "border-b-1 border-base-300 font-semibold",
        onInput(TodoMsg.SetCurrentTitle.apply)
      ),
      svgButton(TodoMsg.AddEntry, "size-8")(
        path(
          d := "M12 9v6m3-3H9m12 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
        )
      )
    )

  def view(model: TodoList): Html[TodoMsg] =
    div(
      cls := "flex flex-col items-center gap-10"
    )(
      input(
        value := model.title,
        cls := "text-5xl font-weight text-center border-b-2 border-base py-3",
        onInput(TodoMsg.SetListTitle.apply)
      ),
      div(cls := "w-full max-w-4xl flex flex-col gap-4")(
        model.entries.zipWithIndex.map(todoEntry)
        :+ addBox
      ),
    )
