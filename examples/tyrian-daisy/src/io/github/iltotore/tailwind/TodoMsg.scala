package io.github.iltotore.tailwind

enum TodoMsg:
  case SetListTitle(title: String)
  case SetCurrentTitle(title: String)
  case AddEntry
  case RemoveEntry(id: Int)
  case SetEntryTitle(id: Int, title: String)
  case SetEntryDescription(id: Int, description: String)
  case ToogleCompleted(id: Int)
  case ToogleOpen(id: Int)
  case NoOp