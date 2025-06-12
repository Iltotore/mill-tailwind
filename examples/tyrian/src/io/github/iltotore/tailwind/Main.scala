package io.github.iltotore.tailwind

import cats.effect.IO
import tyrian.Html.*
import tyrian.*

import scala.scalajs.js.annotation.*

/* 
Generated classes so they can be scanned by Tailwind:
  hover:bg-red-500
  border-red-500
  text-red-500
  hover:bg-cyan-500
  border-cyan-500
  text-cyan-500
*/

type Model = Int
enum Msg:
  case Incr
  case Decr
  case NoOp

@JSExportTopLevel("TyrianApp")
object Counter extends TyrianIOApp[Msg, Model]:

  def router: Location => Msg = Routing.none(Msg.NoOp)

  def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    (0, Cmd.None)

  def update(model: Model): Msg => (Model, Cmd[IO, Msg]) =
    case Msg.Incr => (model + 1, Cmd.None)
    case Msg.Decr => (model - 1, Cmd.None)
    case Msg.NoOp => (model, Cmd.None)

  def subscriptions(model: Model): Sub[IO, Msg] =
    Sub.None

  //Component example
  def counterButton(text: String, color: String)(onClickMsg: Msg): Html[Msg] =
    button(
      onClick(onClickMsg),
      cls :=
        s"""
        text-3xl rounded-xl border border-3 size-15 text-$color border-$color
        hover:bg-$color hover:text-white
        active:scale-90 duration-100
        """
    )(text)

  def view(model: Model): Html[Msg] =
    div(cls := "w-screen h-screen flex justify-center items-center")(
      div(cls := "w-full max-w-2xl flex justify-between items-center")(
        counterButton("-", "red-500")(Msg.Decr),
        label(cls := "text-5xl")(model.toString),
        counterButton("+", "cyan-500")(Msg.Incr)
      )
    )
