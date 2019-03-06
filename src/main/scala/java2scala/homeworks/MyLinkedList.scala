package java2scala.homeworks

sealed trait MyLinkedList[T] extends Traversable[T] {
  def add(element: T): MyLinkedList[T] = ???/*new MyLinkedList[T] {
    val list: MyLinkedList[T] = new MyLinkedList[T]{}
    for (el <- this.toList) {
      list.add(el)
    }
    list.add(element)
    return list
  }*/

  def delete(element: T): MyLinkedList[T] = ???
  def foreach[U](action: T => U): Unit = ???
}


object MyLinkedList {
  def apply[T](kvs: T*): MyLinkedList[T] = ???

  final case class Cons[T](element: T, rest: MyLinkedList[T]) extends MyLinkedList[T]
  final case class Empty[T]() extends MyLinkedList[T]

}

//object ConsTutorialDriver extends App {
//  val list = MyLinkedList[String]()
//
//  list.add("First")
//  list.add("Second")
//  list.add("Third")
//}