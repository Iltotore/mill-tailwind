package io.github.iltotore.tailwind

case class TodoList(title: String, entries: List[TodoEntry], currentTitle: String):
  def updateEntry(id: Int)(f: TodoEntry => TodoEntry): TodoList =
    entries.lift(id).fold(this)(entry => this.copy(entries = entries.updated(id, f(entry))))

  def newEntry: TodoList =
    this.copy(entries = entries :+ TodoEntry.init(currentTitle), currentTitle = "")

  def removeEntry(id: Int): TodoList =
    this.copy(entries = entries.patch(id, Nil, 1))

object TodoList:
  val init: TodoList = TodoList("TODO", List(TodoEntry("Foo", "Lorem ipsum...", false, false)), "")