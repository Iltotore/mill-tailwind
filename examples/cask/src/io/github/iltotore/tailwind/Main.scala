package io.github.iltotore.tailwind7

import scalatags.Text.TypedTag
import scalatags.Text.all.*

object Main extends cask.MainRoutes:

  //Default directory in which Tailwind's output is generated.
  @cask.staticResources("/public")
  def publicRoutes() = "public"

  //Component example
  def helloWorld(name: String): TypedTag[String] =
    h1(
      cls := "text-center text-amber-500 text-5xl font-bold"
    )(s"Bonjour $name")

  @cask.get("/")
  def index(name: String = "World"): TypedTag[String] =
    html(
      head(
        link(rel := "stylesheet", href := "/public/output.css")
      ),
      body(cls := "w-full h-full flex flex-col flex justify-center align-center")(
        helloWorld(name)
      )
    )

  initialize()