package io.github.iltotore.tailwind

case class TodoEntry(title: String, description: String, completed: Boolean, opened: Boolean):

  def toogleCompleted: TodoEntry = this.copy(completed = !completed)
  def toogleOpened: TodoEntry = this.copy(opened = !opened)

object TodoEntry:
  def init(title: String): TodoEntry = TodoEntry(title, "", false, false)