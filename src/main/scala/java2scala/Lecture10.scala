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

  final class Wrapped(val int: Int) {
    def bar = int - 1
  }

  implicit def wrap1[A](a: A)(implicit conv: A => Int): Wrapped = new Wrapped(conv(a))

  final class Wrapped2(w: Wrapped) {
    def foo = w.int + 1
  }

  implicit def wrap2[A](a: A)(implicit conv: A => Wrapped): Wrapped2 = new Wrapped2(conv(a))


  1.bar

  2.foo

}
