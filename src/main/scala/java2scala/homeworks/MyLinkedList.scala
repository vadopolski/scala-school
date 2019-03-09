package java2scala.homeworks

sealed trait MyLinkedList[+T] {
  def add[S >: T](element: S): MyLinkedList[S]
}

case object MyLinkedList {
  def apply[T](elements: T*): MyLinkedList[T] = {
    if (elements.isEmpty) {
      Empty
    } else {
      Cons(elements.head, apply(elements.tail: _*))
    }
  }

  final case class Cons[+T](element: T, rest: MyLinkedList[T]) extends MyLinkedList[T] {
    def add[S >: T](el: S) = Cons(el, Cons(element, rest))
  }

  case object Empty extends MyLinkedList[Nothing] {
    override def add[S >: Nothing](x: S): MyLinkedList[S] = Cons(x, Empty)
  }

  def main(args: Array[String]): Unit = {
    val list = MyLinkedList[String]("Fisrt")
    val list2 = list.add("Second")
    val list3 = list.add("Third")
    println("Test")
  }
}