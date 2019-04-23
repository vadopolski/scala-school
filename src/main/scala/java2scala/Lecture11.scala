package java2scala

import scala.annotation.unchecked.uncheckedVariance
import scala.collection.mutable

object Lecture11 {

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
    def _1: A                                              = first
    def withFirst(newFirst: A @uncheckedVariance): Pair[A] = Pair(newFirst, second)
  }



}
