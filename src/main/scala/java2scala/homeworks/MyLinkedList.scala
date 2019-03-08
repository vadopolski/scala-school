package java2scala.homeworks

sealed trait MyLinkedList[T] {
  def add(element: T): MyLinkedList[T]
}

object MyLinkedList {
  def apply[T](kvs: T*): MyLinkedList[T]

  final case class Cons[T](element: T, rest: MyLinkedList[T]) extends MyLinkedList[T] {
    override def add(element: T): MyLinkedList[T] = ???
  }
  final case class Empty[T]() extends MyLinkedList[T] {
    override def add(element: T): MyLinkedList[T] = ???
  }
}

object ConsTutorialDriver extends App {
  val list = MyLinkedList[String]()

  list.add("First")
  list.add("Second")
  list.add("Third")
}