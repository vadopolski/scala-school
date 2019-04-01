abstract class MyList[+A]

case class MyCons[A](head: A, tail: MyList[A]) extends MyList[A]

case object MyNil extends MyList[Nothing]

val list = MyCons(1, MyCons(2, MyCons(3, MyNil)))

list.tail