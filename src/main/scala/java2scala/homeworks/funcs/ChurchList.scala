package java2scala.homeworks.funcs

trait ChurchList[A] {
  def fold[B](z: B)(f: A => B => B): B

  def ::(x: A): ChurchList[A]                          = ???
  def map[B](f: A => B): ChurchList[B]                 = ???
  def filter(f: A => Boolean): ChurchList[A]           = ???
  def flatMap[B](f: A => ChurchList[B]): ChurchList[B] = ???
  def headOption: Option[A]                            = ???
  def drop(count: Int): ChurchList[A]                  = ???
  def toList: List[A]                                  = ???
  def ++(that: ChurchList[A]): ChurchList[A]           = ???
}

object ChurchList {
  def apply[A](xs: A*): ChurchList[A] = ???

  def empty[A]: ChurchList[A] = ???
}
