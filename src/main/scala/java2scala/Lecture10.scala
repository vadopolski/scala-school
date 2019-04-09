package java2scala

object Lecture10 extends App {
  def fibonacci[N](implicit N: Numeric[N]): Stream[N] = {
    lazy val res: Stream[N] = N.one #:: N.one #:: res.zip(res.tail).map { case (x, y) => N.plus(x, y) }
    res
  }

  println(fibonacci[Int].take(1000).force)
  println(fibonacci[BigInt].take(1000).force)
}
