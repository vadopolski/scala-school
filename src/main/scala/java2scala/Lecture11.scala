package java2scala

import scala.annotation.unchecked.uncheckedVariance
import scala.collection.mutable

object Lecture11 extends App {

  abstract class X {
    def x: String
  }

  abstract class Y extends X {
    def y: Int
  }

  def foo(x: X): String = x.x

  def bar(y: Y): String = foo(y)

  def fooL(xs: List[X]): String = xs.map(_.x).mkString(",")

  def barL(ys: List[Y]): String = fooL(ys)

  case class Pair[+A](first: A, second: A) {
    def _1: A                     = first
    var mid: A @uncheckedVariance = first
  }

  val pairOfYs: Pair[Y] = Pair(new Y { def x = "a"; def y = 1 }, new Y { def x = "b"; def y = 2 })
  (pairOfYs: Pair[X]).mid = new X { def x = "c" }

  println(pairOfYs.mid.y)

}
