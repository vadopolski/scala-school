package java2scala

import Numeric.Implicits._
object Lecture10 extends App {
  def one[N](implicit N: Numeric[N]): N = N.one

  def fibonacci[N: Numeric]: Stream[N] = {
    lazy val res: Stream[N] = one #:: one #:: res.zip(res.tail).map { case (x, y) => x + y }
    res
  }

  println(fibonacci[Int].take(1000).force)
  println(fibonacci[BigInt].take(1000).force)
}
