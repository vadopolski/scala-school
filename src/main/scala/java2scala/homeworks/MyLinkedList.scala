package java2scala.homeworks

sealed trait MyLinkedList[+T] {
  def add[S >: T](element: S): MyLinkedList[S]
}

case object MyLinkedList {
  def apply[T](elements: T*): MyLinkedList[T] = {
    if (elements.isEmpty) {
      EmptyLL
    } else {
      ConsLL(elements.head, apply(elements.tail: _*))
    }
  }

  final case class ConsLL[+T](element: T, rest: MyLinkedList[T]) extends MyLinkedList[T] {
    def add[S >: T](el: S) = ConsLL(el, ConsLL(element, rest))
  }

  case object EmptyLL extends MyLinkedList[Nothing] {
    override def add[S >: Nothing](x: S): MyLinkedList[S] = ConsLL(x, EmptyLL)
  }

  def main(args: Array[String]): Unit = {
    val list = MyLinkedList[String]("Fisrt")
    val list2 = list.add("Second")
    val list3 = list.add("Third")
    println("Test")
  }
}