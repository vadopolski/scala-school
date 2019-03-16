package java2scala.homeworks.funcs

trait ChurchTuple[A, B] {
  def unpack[C](f: A => B => C): C

  def first: A  = ???
  def second: B = ???

  def mapFirst[C](f: A => C): ChurchTuple[C, B]  = ???
  def mapSecond[C](f: B => C): ChurchTuple[A, C] = ???
}

object ChurchTuple {
  def apply[A, B](x: A, y: B): ChurchTuple[A, B] = ???
}
